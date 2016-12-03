package com.dingmouren.easymvp.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.view.Gravity;

/**
 * Created by dingmouren on 2016/12/1.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private static final int SLIDE_TRANSITION_TIME = 1 * 1000;
    public Slide mSlideTransition;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupWindowAnimation();
    }

    protected  void setupWindowAnimation(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mSlideTransition = new Slide();
            mSlideTransition.setDuration(SLIDE_TRANSITION_TIME);
            mSlideTransition.setSlideEdge(Gravity.LEFT);
            getWindow().setEnterTransition(mSlideTransition);
            getWindow().setExitTransition(mSlideTransition);
        }
    }
}
