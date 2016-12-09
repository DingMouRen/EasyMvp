package com.dingmouren.easymvp.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.dingmouren.easymvp.R;
import com.dingmouren.easymvp.base.BaseActivity;
import com.dingmouren.easymvp.ui.home.HomeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dingmouren on 2016/12/3.
 */

public class GalleryActivity extends BaseActivity{

    @BindView(R.id.toolbar)  Toolbar mToolbar;
    @BindView(R.id.frame_gallery)  FrameLayout mFrameGallery;
    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_gallery;
    }


    @Override
    protected void setUpView() {
        //初始化toolbar
        initToolbar();
        //初始化fragment
        initFragment();
    }

    @Override
    protected void setUpData() {

    }

    private void initToolbar() {
        mToolbar.setTitle("相册");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(mArrowListener );
    }


    private void initFragment() {
        getSupportFragmentManager().beginTransaction().add(R.id.frame_gallery,new GalleryFragment()).commit();
    }

    View.OnClickListener mArrowListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(GalleryActivity.this, HomeActivity.class));
        }
    };

}
