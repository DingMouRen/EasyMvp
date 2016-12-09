package com.dingmouren.easymvp.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;

import butterknife.ButterKnife;

/**
 * Created by dingmouren on 2016/12/1.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private static final int SLIDE_TRANSITION_TIME = 1 * 1000;
    public Fade mFadeTransition;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);//用于初始化view之前做一些事情
        setContentView(setLayoutResourceID());
        ButterKnife.bind(this);
        setUpView();
        setUpData();
        setupWindowAnimation();//5.0以上的动画
    }
    protected abstract int setLayoutResourceID();
    protected abstract void setUpView();
    protected abstract void setUpData();


    protected  void init(Bundle savedInstanceState){
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
