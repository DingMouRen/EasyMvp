package com.dingmouren.easymvp.ui.video;

import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.dingmouren.easymvp.R;
import com.dingmouren.easymvp.base.BaseActivity;
import com.dingmouren.easymvp.listener.SampleListener;
import com.jiongbull.jlog.JLog;
import com.shuyu.gsyvideoplayer.GSYVideoPlayer;
import com.shuyu.gsyvideoplayer.utils.ListVideoUtil;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dingmouren on 2016/12/6.
 */

public class VideoActivity extends BaseActivity {

    private static final String TAG = VideoActivity.class.getSimpleName();
    @BindView(R.id.toolbar)  Toolbar mToolbar;
    @BindView(R.id.swipe_refresh)  SwipeRefreshLayout mSwipeRefresh;
    @BindView(R.id.recycler) RecyclerView mRecycler;
    @BindView(R.id.video_container) FrameLayout mVideoContainer;

    private ListVideoUtil mListVideoUtil;
    private VideoAdapter mAdapter;
    private int mLastVisibleItem;
    private int mFirstVisibleItem;
    private LinearLayoutManager mLinearLayoutManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        ButterKnife.bind(this);
        //初始化toolbar
        initToolbar();
        //初始化视图以及工具类
        initViewAndUtil();
        //初始化监听
        initListener();

    }

    private void initToolbar() {
        mToolbar.setTitle("段子视频");
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationIcon(R.mipmap.arrow_back);//设置返回箭头图片
        mToolbar.setNavigationOnClickListener(view -> finish());
    }

    private void initViewAndUtil(){
        mListVideoUtil = new ListVideoUtil(this);
        mListVideoUtil.setFullViewContainer(mVideoContainer);//设置全屏时候的根布局,framelayout
        mListVideoUtil.setHideStatusBar(true);//设置隐藏手机状态栏
        mListVideoUtil.setHideActionBar(true);//播放视频时隐藏toolbar

        mLinearLayoutManager = new LinearLayoutManager(this);
        mAdapter = new VideoAdapter(this,mListVideoUtil);
        mRecycler.setLayoutManager(mLinearLayoutManager);
        mRecycler.setHasFixedSize(true);
        mRecycler.setAdapter(mAdapter);
    }

    private void initListener(){
        mRecycler.setOnScrollListener(onScrollListener);
        mListVideoUtil.setVideoAllCallBack(sampleListener);//小窗口点击关闭的时候回掉处理恢复页面
    }


     RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
         @Override
         public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
             super.onScrollStateChanged(recyclerView, newState);
         }

         @Override
         public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
             super.onScrolled(recyclerView, dx, dy);
             mFirstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();
             mLastVisibleItem = mFirstVisibleItem + mLinearLayoutManager.getItemCount();
             //大于0说明有播放，对应的播放列表TAG
             if (mListVideoUtil.getPlayPosition() >= 0 && mListVideoUtil.getPlayTAG().equals(VideoAdapter.TAG)){
                 //播放当前的位置
                 int position = mListVideoUtil.getPlayPosition();
                 //不可视的时候
                 if ((mFirstVisibleItem > position || mLastVisibleItem < position)){
                     //如果是小窗口就不需要处理
                     if (!mListVideoUtil.isSmall()){
                         //设置小窗口的宽高
                         int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,208,getResources().getDisplayMetrics());
                         int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,117,getResources().getDisplayMetrics());
                         mListVideoUtil.showSmallVideo(new Point(width,height),false,true);
                     }
                 }else {
                     if (mListVideoUtil.isSmall()) mListVideoUtil.smallVideoToNormal();
                 }
             }
         }
     };

    SampleListener sampleListener = new SampleListener(){
        @Override
        public void onPrepared(String url, Object... objects) {
            super.onPrepared(url, objects);
            JLog.d("Duration : " + mListVideoUtil.getDuration() + "  CurrentPosition : " + mListVideoUtil.getCurrentPositionWhenPlaying());
        }

        @Override
        public void onQuitSmallWidget(String url, Object... objects) {
            super.onQuitSmallWidget(url, objects);
            //大于0说明有播放，对应播放列表TAG
            if (mListVideoUtil.getPlayPosition() >= 0 && mListVideoUtil.getPlayTAG().equals(VideoAdapter.TAG)){
                //当前播放的位置
                int positon = mListVideoUtil.getPlayPosition();
                //不可视的时候
                if (positon < mFirstVisibleItem || positon > mLastVisibleItem){
                    //释放掉视频
                    mListVideoUtil.releaseVideoPlayer();
                    mAdapter.notifyDataSetChanged();
                }
            }
        }
    };

    @Override
    public void onBackPressed() {
        if (mListVideoUtil.backFromFull()){
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mListVideoUtil.releaseVideoPlayer();
        GSYVideoPlayer.releaseAllVideos();
    }
}
