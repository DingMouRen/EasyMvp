package com.dingmouren.easymvp.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.dingmouren.easymvp.Constant;
import com.dingmouren.easymvp.R;
import com.dingmouren.easymvp.base.BaseActivity;
import com.dingmouren.easymvp.event.NightModeChangeEvent;
import com.dingmouren.easymvp.ui.home.HomeActivity;
import com.dingmouren.easymvp.util.SPUtil;
import com.dingmouren.easymvp.util.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dingmouren on 2016/12/3.
 */

public class GalleryActivity extends BaseActivity{

    @BindView(R.id.toolbar)  Toolbar mToolbar;
    @BindView(R.id.frame_gallery)  FrameLayout mFrameGallery;
    @BindView(R.id.relative_gallery) RelativeLayout mRelativeLayout;
    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_gallery;
    }


    @Override
    protected void setUpView() {
        EventBus.getDefault().register(this);//注册事件总线
        initToolbar();//初始化toolbar
        initNightMode();//初始化夜间模式
    }

    @Override
    protected void setUpData() {
        getSupportFragmentManager().beginTransaction().add(R.id.frame_gallery,new GalleryFragment()).commit();
    }

    /**
     * 初始化toolbar
     */
    private void initToolbar(){
        mToolbar.setTitle("相册");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(mArrowListener );
    }

    View.OnClickListener mArrowListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };

    //接收到切换夜间模式的通知，进行对应的夜间模式的切换
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void changeNightMode(NightModeChangeEvent event){
        /*if ((Boolean) SPUtil.get(GalleryActivity.this, Constant.NIGHT_MODE,true)){
            mRelativeLayout.setBackgroundColor(getResources().getColor(R.color.gray));
            mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            StatusBarUtil.setStatusBarColor(GalleryActivity.this,getResources().getColor(R.color.colorPrimaryDark));
        }else {
            mRelativeLayout.setBackgroundColor(getResources().getColor(android.R.color.black));
            mToolbar.setBackgroundColor(getResources().getColor(android.R.color.black));
            StatusBarUtil.setStatusBarColor(GalleryActivity.this,getResources().getColor(android.R.color.black));
        }*/
        initNightMode();
    }

    /**
     * 初始化夜间模式
     */
    private void initNightMode(){
        if ((Boolean) SPUtil.get(GalleryActivity.this, Constant.NIGHT_MODE,true)){
            mRelativeLayout.setBackgroundColor(getResources().getColor(R.color.gray));
            mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
            mToolbar.setNavigationIcon(R.mipmap.arrow_back);
            StatusBarUtil.setStatusBarColor(GalleryActivity.this,getResources().getColor(R.color.colorPrimaryDark));
        }else {
            mRelativeLayout.setBackgroundColor(getResources().getColor(android.R.color.black));
            mToolbar.setBackgroundColor(getResources().getColor(android.R.color.black));
            mToolbar.setTitleTextColor(getResources().getColor(android.R.color.darker_gray));
            mToolbar.setNavigationIcon(R.mipmap.arrow_back_night);
            StatusBarUtil.setStatusBarColor(GalleryActivity.this,getResources().getColor(android.R.color.black));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//解绑事件总线
    }
}
