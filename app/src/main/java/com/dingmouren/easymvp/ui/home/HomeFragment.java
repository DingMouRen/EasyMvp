package com.dingmouren.easymvp.ui.home;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dingmouren.easymvp.R;
import com.dingmouren.easymvp.base.BaseFragment;
import com.dingmouren.easymvp.bean.gank.GankContent;
import com.dingmouren.easymvp.event.NightModeChangeEvent;
import com.dingmouren.easymvp.ui.home.layouts.GankHomeViewProvider;
import com.dingmouren.easymvp.ui.home.layouts.ImgHome;
import com.dingmouren.easymvp.ui.home.layouts.ImgHomeViewProvider;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by dingmouren on 2016/12/8.
 */

public class HomeFragment extends BaseFragment implements HomeContract.View {

    @BindView(R.id.recycler)  RecyclerView mRecycler;
    @BindView(R.id.relative_progressbar)  RelativeLayout mRelativeProgressbar;
    @BindView(R.id.tv_home_progress)  TextView mProgressTextView;
    @BindView(R.id.progressbar_home) ProgressBar mProgressbar;

    private HomePresenter mPresenter;
    private List<Object> mItems;
    private MultiTypeAdapter mMultiTypeAdapter;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_home;
    }

    @Override
    protected void init() {
        //初始化布局容器和适配器
        mItems = new ArrayList<>();
        mMultiTypeAdapter = new MultiTypeAdapter(mItems);

        //注册布局
        mMultiTypeAdapter.register(ImgHome.class,new ImgHomeViewProvider());
        mMultiTypeAdapter.register(GankContent.class,new GankHomeViewProvider());
    }

    @Override
    protected void setUpView() {
        EventBus.getDefault().register(this);//注册事件总线
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycler.setHasFixedSize(true);
    }

    @Override
    protected void setUpData() {
        mPresenter = new HomePresenter((HomeContract.View)this);
        mPresenter.requestData();

    }

    @Override
    public void setDataRefresh(boolean refresh) {
        if (refresh){
            showLoading(refresh);
        }else {
            showLoading(refresh);
        }
    }

    /**
     * 显示数据
     */
    @Override
    public void notifyDataSetChanged() {
        mRecycler.setAdapter(mMultiTypeAdapter);
        setDataRefresh(false);
    }

    @Override
    public RelativeLayout getProgressBarRelative() {
        return mRelativeProgressbar;
    }

    @Override
    public TextView getProgressBarTextView() {
        return mProgressTextView;
    }

    @Override
    public ProgressBar getProgressbar() {
        return mProgressbar;
    }

    @Override
    public List<Object> getItems() {
        return mItems;
    }

    /**
     * 刷新时的loading
     * @param refresh
     */
    private void showLoading(boolean refresh){
        if (refresh){
            mProgressbar.setIndeterminateDrawable(getResources().getDrawable(R.drawable.progressbar_circle));
            mProgressbar.setProgressDrawable(getResources().getDrawable(R.drawable.progressbar_circle));
            mRelativeProgressbar.setVisibility(View.VISIBLE);
            mProgressTextView.setText("正在拼命加载中。。。");
        }else {
            mRelativeProgressbar.setVisibility(View.GONE);
        }
    }

    //接收到改变模式的通知，重新刷新视图
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void changeNightMode(NightModeChangeEvent event){
        mMultiTypeAdapter.notifyDataSetChanged();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);//解绑事件总线
    }
}
