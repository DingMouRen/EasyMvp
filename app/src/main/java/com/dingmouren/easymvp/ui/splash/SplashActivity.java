package com.dingmouren.easymvp.ui.splash;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.transition.Fade;
import android.widget.TextView;

import com.dingmouren.easymvp.R;
import com.dingmouren.easymvp.base.BaseActivity;
import com.dingmouren.easymvp.ui.home.HomeActivity;
import com.dingmouren.easymvp.view.CustomVideoView;
import com.dingmouren.easymvp.view.TimerTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dingmouren on 2016/12/3.
 */

public class SplashActivity extends BaseActivity  {

    private  String VIDEO_PATH = "android.resource://com.dingmouren.easymvp/" + R.raw.video;

    @BindView(R.id.video_view)  CustomVideoView mVideoView;
    @BindView(R.id.tv_splash)  TimerTextView mTimer;
    private Uri mUri;
    private MediaPlayer mMediaPlayer;
    private ObjectAnimator mObjectAnimator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        //初始化倒计时
        initTimer(30);
        //播放视频
        playVideo();


    }

    private void initTimer(long time) {
        mTimer.setTimes(time);
        mTimer.beginRun();
    }

    //渐变的属性动画，这里没有用到
    private void alphaAnimate() {
        mObjectAnimator = ObjectAnimator.ofFloat(mVideoView,"alpha",0f,1f);
        mObjectAnimator.setDuration(1000 );
        mObjectAnimator.start();
    }

    private void playVideo() {
        mUri = Uri.parse(VIDEO_PATH);
        mVideoView.playVideo(mUri);
        mMediaPlayer = MediaPlayer.create(this,R.raw.igotyou);
        mMediaPlayer.start();
        mMediaPlayer.setLooping(true);
    }

    @OnClick(R.id.tv_splash)
     void onClick(){
        if (!mTimer.isRun()) {
            startActivity(new Intent(SplashActivity.this, HomeActivity.class));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            mMediaPlayer.stop();
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mVideoView != null & mUri != null & mMediaPlayer != null){
            mVideoView.stopPlayback();
            mMediaPlayer.release();
            mMediaPlayer = null;
            mUri = null;
        }

    }
}
