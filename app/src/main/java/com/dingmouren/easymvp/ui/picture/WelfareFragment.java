package com.dingmouren.easymvp.ui.picture;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dingmouren.easymvp.Constant;
import com.dingmouren.easymvp.R;
import com.dingmouren.easymvp.base.BaseFragment;
import com.dingmouren.easymvp.bean.GankResultWelfare;
import com.dingmouren.easymvp.event.NightModeChangeEvent;
import com.dingmouren.easymvp.ui.picture.layouts.WelfareImageViewProvider;
import com.dingmouren.easymvp.util.SPUtil;
import com.dingmouren.easymvp.util.SnackbarUtils;
import com.jiongbull.jlog.JLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by dingmouren on 2016/12/6.
 */

public class WelfareFragment extends BaseFragment implements WelfareContract.View{
    public static final String TAG = WelfareFragment.class.getName();
    @BindView(R.id.swipe_refresh)  SwipeRefreshLayout mSwipeRefresh;
    @BindView(R.id.recycler)  RecyclerView mRecycler;
    private List<Object> mItems;
    private MultiTypeAdapter mMultiTypeAdapter;

    private GridLayoutManager mGridLayoutManager;
    public WelfarePresenter mWelfarePresenter;
    public boolean isNullDatabase = true;//设置标记，如果数据库没有数据，设置为true

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_welfare;
    }

    @Override
    protected void init() {
        mItems = new ArrayList<>();
        mMultiTypeAdapter = new MultiTypeAdapter(mItems);
        mMultiTypeAdapter.register(GankResultWelfare.class,new WelfareImageViewProvider());
    }

    @Override
    protected void setUpView() {
        EventBus.getDefault().register(this);//注册事件总线
        //初始化SwipeRefresh的颜色
        if (mSwipeRefresh != null){
            mSwipeRefresh.setColorSchemeResources(R.color.main_color);//设置进度动画的颜色
//            mSwipeRefresh.setProgressBackgroundColorSchemeResource(android.R.color.holo_blue_bright);//设置进度圈背景颜色
            //这里进行单位换算  第一个参数是单位，第二个参数是单位数值，这里最终返回的是24dp对相应的px值
            mSwipeRefresh.setProgressViewOffset(true,0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,24,getResources().getDisplayMetrics()));
            mSwipeRefresh.setOnRefreshListener(()-> setDataRefresh(false));
        }
        //列表相关
        mGridLayoutManager = new GridLayoutManager(getActivity(),2);
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(mGridLayoutManager);
        mRecycler.setAdapter(mMultiTypeAdapter);
    }

    @Override
    protected void setUpData() {
        mWelfarePresenter = createPresenter();
        setDataRefresh(true);
        if ( !mWelfarePresenter.setDataFormDatabase()){//当从数据库取出的数据为空时，去请求最新一页网络数据
            JLog.e(TAG,"数据库没取出数据" );
            mWelfarePresenter.requestData();
        }else {
            isNullDatabase = false;
            JLog.e(TAG,"数据库取出数据 ");
        }

        mWelfarePresenter.addScrollListener();
    }

    //接收到夜间模式变化的通知时，刷新视图
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void changeNightMode(NightModeChangeEvent event){
        if ((Boolean) SPUtil.get(getContext(), Constant.NIGHT_MODE,true)){
            mSwipeRefresh.setBackgroundColor(getResources().getColor(R.color.gray));
        }else {
            mSwipeRefresh.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
        }
    }
    // 获取presenter实例
    public WelfarePresenter createPresenter(){
        return new WelfarePresenter((WelfareContract.View) this);
    }
    @Override
    public void setDataRefresh(boolean refresh) {
        if (refresh){
            mSwipeRefresh.setRefreshing(refresh);
        }else {
            new Handler().postDelayed(()->mSwipeRefresh.setRefreshing(refresh),1000);//延时消失加载的loading
        }
    }

    @Override
    public GridLayoutManager getLayoutManager() {
        return  mGridLayoutManager == null ? new GridLayoutManager( getActivity(),2 ) : mGridLayoutManager;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mRecycler;
    }



    @Override
    public boolean getIsNullDatabase() {
        return isNullDatabase;
    }

    @Override
    public void notifyDataSetChanged() {
        mMultiTypeAdapter.notifyDataSetChanged();
        setDataRefresh(false);
    }

    @Override
    public List<Object> getItems() {
        return mItems;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);//解绑事件总线
    }
}
