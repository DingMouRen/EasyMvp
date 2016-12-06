package com.dingmouren.easymvp.ui.video;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.dingmouren.easymvp.R;
import com.dingmouren.easymvp.base.BaseActivity;
import com.shuyu.gsyvideoplayer.GSYVideoPlayer;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dingmouren on 2016/12/6.
 */

public class VideoActivity extends BaseActivity {

    private static final String VIDEO_URL = "http://baobab.wdjcdn.com/14564977406580.mp4";

    @BindView(R.id.video_player)   StandardGSYVideoPlayer mVideoPlayer;

    private ImageView mImageViewCover;//用于封装要在设置的封面
    private OrientationUtils mOrientationUtils;//旋转帮助类
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        ButterKnife.bind(this);
        //初始化video
        initVideo();
    }

    private void initVideo() {

        //默认的缓存路径方式
        mVideoPlayer.setUp(VIDEO_URL,true,"");

        //设置封面
        mImageViewCover = new ImageView(this);
        mImageViewCover.setScaleType(ImageView.ScaleType.CENTER_CROP);
        mImageViewCover.setImageResource(R.mipmap.video_cover);
        mVideoPlayer.setThumbImageView(mImageViewCover);

        //增加title
        mVideoPlayer.getTitleTextView().setVisibility(View.VISIBLE);
        mVideoPlayer.getTitleTextView().setText("莫斯卡获奖");

        //设置返回键,点击返回
        mVideoPlayer.getBackButton().setVisibility(View.VISIBLE);
        mVideoPlayer.getBackButton().setOnClickListener((view) -> onBackPressed());

        //设置旋转
        mOrientationUtils = new OrientationUtils(this,mVideoPlayer);

        //设置全屏按键功能
        mVideoPlayer.getFullscreenButton().setOnClickListener((view) -> mOrientationUtils.resolveByClick());

        //是否可以滑动调整
        mVideoPlayer.setIsTouchWiget(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mVideoPlayer.onVideoPause();
    }

    @Override
    public void onBackPressed() {
       //返回正常状态
        if (mOrientationUtils.getScreenType() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            mVideoPlayer.getFullscreenButton().performClick();
            return;
        }
        //释放所有
        mVideoPlayer.setStandardVideoAllCallBack(null);
        GSYVideoPlayer.releaseAllVideos();
        super.onBackPressed();
    }
}
