package com.dingmouren.easymvp.ui.videos;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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
    }

    @Override
    protected void setUpData() {
        mPresenter.requestData();
    }

    @Override
    public void setData() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public List<VideoBean> getVideoAdapterList() {
        return mList;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //释放资源
        GSYVideoPlayer.releaseAllVideos();
    }
}
