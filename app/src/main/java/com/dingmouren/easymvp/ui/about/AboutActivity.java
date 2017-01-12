package com.dingmouren.easymvp.ui.about;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.internal.NavigationMenu;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.dingmouren.easymvp.R;
import com.dingmouren.easymvp.base.BaseActivity;

import butterknife.BindView;
import io.github.yavski.fabspeeddial.FabSpeedDial;
import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by dingmouren on 2016/12/10.
 *
 */

public class AboutActivity extends BaseActivity {

    @BindView(R.id.title_toolbar)  Toolbar mTitleToolbar;
    @BindView(R.id.collapsing)  CollapsingToolbarLayout mCollapsing;
    @BindView(R.id.nestedScrollView)  NestedScrollView mNestedScrollView;
    @BindView(R.id.fab_about)FloatingActionButton mFab;

    public static void newInstance(Context context){
        context.startActivity(new Intent(context,AboutActivity.class));
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_about;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
//        setBlur();
    }

    @Override
    protected void setUpView() {
        mTitleToolbar.setTitle("");
        mTitleToolbar.setLogo(R.drawable.about_header);
        setSupportActionBar(mTitleToolbar);

        mCollapsing.setTitle("钉某人");
        mCollapsing.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        mCollapsing.setCollapsedTitleTextColor(getResources().getColor(android.R.color.white));

        mFab.setOnClickListener((view) -> finish());

    }


    @Override
    protected void setUpData() {

    }


    /**
     * 设置毛玻璃效果
     */
    private void setBlur(){
        Glide.with(this).load(R.mipmap.about).bitmapTransform(new BlurTransformation(this,100))
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                    }
                });
    }
}
