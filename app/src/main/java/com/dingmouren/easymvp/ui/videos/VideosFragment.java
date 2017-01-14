package com.dingmouren.easymvp.ui.videos;

import android.graphics.Bitmap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.ProgressBar;

import com.dingmouren.easymvp.R;
import com.dingmouren.easymvp.base.BaseFragment;
import com.dingmouren.easymvp.bean.video.VideoBean;
import com.shuyu.gsyvideoplayer.GSYVideoPlayer;

import java.util.List;

import butterknife.BindView;

/**
 * Created by dingmouren on 2017/1/12.
 */

public class VideosFragment extends BaseFragment implements VideoContract.View{
    @BindView(R.id.recycler) RecyclerView mRecycler;
    @BindView(R.id.swipe_refresh) SwipeRefreshLayout mSwipeRefresh;
    @BindView(R.id.pb) ProgressBar mPb;
    public List<VideoBean> mList;
    private VideoPresenter mPresenter;
    private LinearLayoutManager mLinearLayoutManager;
    private VideoAdapter mAdapter;
    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_videos;
    }

    @Override
    protected void setUpView() {
        mAdapter = new VideoAdapter(getContext());
        mList = mAdapter.getmList();
        //RecyclerView相关
        mLinearLayoutManager = new LinearLayoutManager(mRecycler.getContext());
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(mLinearLayoutManager);
        mRecycler.setAdapter(mAdapter);
        //Presenter相关
        mPresenter = new VideoPresenter((VideoContract.View)this);

        //初始化SwipeRefresh的颜色
        if (mSwipeRefresh != null) {
            mSwipeRefresh.setColorSchemeResources(R.color.main_color);//设置进度动画的颜色
//            mSwipeRefresh.setProgressBackgroundColorSchemeResource(android.R.color.holo_blue_bright);//设置进度圈背景颜色
            //这里进行单位换算  第一个参数是单位，第二个参数是单位数值，这里最终返回的是24dp对相应的px值
            mSwipeRefresh.setProgressViewOffset(true, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
        }
        mSwipeRefresh.setOnRefreshListener(()->mPresenter.requestData());//刷新就请求第一页数据
    }

    @Override
    protected void setUpData() {
        setRefresh(true);
        mPresenter.requestData();
        mPresenter.addScrollListener();
    }

    @Override//加载了新数据就要刷新列表
    public void notifyDataSetChanged() {
        mAdapter.notifyDataSetChanged();
        setRefresh(false);
        loadMore(false);
    }

    @Override
    public List<VideoBean> getVideoAdapterList() {
        return mList;
    }

    @Override
    public void setRefresh(boolean refresh) {
        if (refresh){
            mSwipeRefresh.setRefreshing(true);
        }else {
            mSwipeRefresh.setRefreshing(false);
        }
    }

    @Override
    public void loadMore(boolean loadMore) {
        if (loadMore){
            mPb.setVisibility(View.VISIBLE);
        }else {
            mPb.setVisibility(View.GONE);
        }
    }

    @Override
    public LinearLayoutManager getLayoutManager() {
        return mLinearLayoutManager;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mRecycler;
    }

    @Override
    public boolean isRefreshingg() {
        return mSwipeRefresh.isRefreshing();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //释放资源
        GSYVideoPlayer.releaseAllVideos();
    }
}
