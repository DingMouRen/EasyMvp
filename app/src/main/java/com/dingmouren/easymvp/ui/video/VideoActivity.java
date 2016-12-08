package com.dingmouren.easymvp.ui.video;

import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.widget.FrameLayout;

import com.dingmouren.easymvp.R;
import com.dingmouren.easymvp.base.BaseActivity;
import com.dingmouren.easymvp.listener.SampleListener;
import com.dingmouren.easymvp.util.video_helper.CustomListVideoUtil;
import com.dingmouren.easymvp.util.video_helper.CustomStandardGSYVideoPlayer;
import com.jiongbull.jlog.JLog;
import com.shuyu.gsyvideoplayer.GSYVideoPlayer;

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

    private CustomListVideoUtil mListVideoUtil;
    private VideoAdapter mAdapter;
    private int mLastVisibleItem;
    private int mFirstVisibleItem;
    private LinearLayoutManager mLinearLayoutManager;
    private CustomStandardGSYVideoPlayer mStandardGSYVideoPlayer;
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
        mListVideoUtil = new CustomListVideoUtil(this);
        mStandardGSYVideoPlayer = mListVideoUtil.getGsyVideoPlayer();
        mStandardGSYVideoPlayer.setDialogVolumeProgressBar(getResources().getDrawable(R.drawable.progressbar_video_volume));//设置声音进度条的颜色
        mStandardGSYVideoPlayer.setBottomProgressBarDrawable(getResources().getDrawable(R.drawable.progressbar_video_bottom));//设置底部不弹出进度条的颜色
        //设置底部弹出进度条的颜色
        mStandardGSYVideoPlayer.setBottomShowProgressBarDrawable(getResources().getDrawable(R.drawable.progressbar_video_bottom),getResources().getDrawable(R.drawable.progressbar_video_bottom));
        mListVideoUtil.setFullViewContainer(mVideoContainer);//设置全屏时候的根布局,framelayout
        mListVideoUtil.setHideStatusBar(true);//设置隐藏手机状态栏
        mListVideoUtil.setHideActionBar(true);//播放视频时隐藏toolbar
        mListVideoUtil.setFullLandFrist(true);//点击最大化，是否立马进入横屏

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
             mLastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();
             //大于0说明有播放，对应的播放列表TAG
             if (mListVideoUtil.getPlayPosition() >= 0 && mListVideoUtil.getPlayTAG().equals(VideoAdapter.TAG)){
                 //播放当前的位置
                 int position = mListVideoUtil.getPlayPosition();
                 //不可视的时候
                 if ((position < mFirstVisibleItem||  position >  mLastVisibleItem)){
                     //如果是小窗口就不需要处理
                     if (!mListVideoUtil.isSmall()){
                         //设置小窗口的宽高
                         int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,208,getResources().getDisplayMetrics());
                         int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,117,getResources().getDisplayMetrics());
                         mListVideoUtil.showSmallVideo(new Point(width,height),false,true);
                         mStandardGSYVideoPlayer.getSmallVideoPlayer().setBottomProgressBarDrawable(null);
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
