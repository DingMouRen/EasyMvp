package com.dingmouren.easymvp.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;

/**
 * Created by dingmouren on 2016/12/1.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private static final int SLIDE_TRANSITION_TIME = 1 * 1000;
    public Fade mFadeTransition;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setupWindowAnimation();
    }

    protected  void setupWindowAnimation(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mFadeTransition = new Fade();
            mFadeTransition.setDuration(SLIDE_TRANSITION_TIME);
            getWindow().setEnterTransition(mFadeTransition);
            getWindow().setExitTransition(mFadeTransition);
        }
    }
}
