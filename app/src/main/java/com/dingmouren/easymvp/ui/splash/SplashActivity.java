package com.dingmouren.easymvp.ui.splash;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.dingmouren.easymvp.R;
import com.dingmouren.easymvp.base.BaseActivity;
import com.dingmouren.easymvp.ui.home.HomeActivity;
import com.dingmouren.easymvp.util.SnackbarUtils;
import com.dingmouren.easymvp.view.CustomVideoView;
import com.dingmouren.easymvp.view.TimerTextView;
import com.jiongbull.jlog.JLog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dingmouren on 2016/12/3.
 */

public class SplashActivity extends BaseActivity {

    private static final String TAG = SplashActivity.class.getName();

    @BindView(R.id.logo_bg)
    ImageView logo_bg;
    @BindView(R.id.logo_text)
    ImageView logo_text;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_splash;
    }

    @Override
    protected void setUpView() {
        startLogoAnimTxt();
        startLogoAnimBg();
    }

    /**
     * logo文字的动画
     */
    private void startLogoAnimTxt() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_splash_top_in);
        logo_text.startAnimation(animation);
    }

    /**
     * logo背景的动画
     */
    private void startLogoAnimBg() {
        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float fraction = valueAnimator.getAnimatedFraction();
                if (fraction >= 0.8) {
                    AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.setDuration(1000);
                    animatorSet.playTogether(ObjectAnimator.ofFloat(logo_bg, "scaleX", new float[]{1.0f, 1.25f, 0.75f, 1.15f, 1.0f}),
                            ObjectAnimator.ofFloat(logo_bg, "scaleY", new float[]{1.0f, 0.75f, 1.25f, 0.85f, 1.0f}));
                    animatorSet.start();
                    animatorSet.addListener(animatorSetListener);
                }
            }

        });
        valueAnimator.start();
    }


    @Override
    protected void setUpData() {

    }

    private void turnToHome() {
        startActivity(new Intent(SplashActivity.this, HomeActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    Animator.AnimatorListener animatorSetListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animator) {

        }

        @Override
        public void onAnimationEnd(Animator animator) {
            new Handler().postDelayed(() -> turnToHome(), 200);
        }

        @Override
        public void onAnimationCancel(Animator animator) {

        }

        @Override
        public void onAnimationRepeat(Animator animator) {

        }
    };


}
