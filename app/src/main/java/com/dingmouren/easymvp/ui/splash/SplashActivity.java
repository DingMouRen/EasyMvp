package com.dingmouren.easymvp.ui.splash;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

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


    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_splash;
    }

    @Override
    protected void setUpView() {

    }

    @Override
    protected void setUpData() {
        new Handler().postDelayed(()->   turnToHome(),1500);
    }

    private void turnToHome(){
        startActivity(new Intent(SplashActivity.this,HomeActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }


//            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

}
