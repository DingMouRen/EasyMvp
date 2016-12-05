package com.dingmouren.easymvp.ui.gallery;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private GalleryFragment mGalleryFragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        ButterKnife.bind(this);
        //初始化toolbar
        initToolbar();
        //初始化fragment
        initFragment();
    }

    private void initToolbar() {
        mToolbar.setTitle("相册");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(mArrowListener );
    }


    private void initFragment() {
        mFragmentManager = getFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mGalleryFragment = new GalleryFragment();
        mFragmentTransaction.replace(R.id.frame_gallery,mGalleryFragment).commit();
    }

    View.OnClickListener mArrowListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(GalleryActivity.this, HomeActivity.class));
        }
    };

}
