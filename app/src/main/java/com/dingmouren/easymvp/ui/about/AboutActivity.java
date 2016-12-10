package com.dingmouren.easymvp.ui.about;

import android.content.Context;
import android.content.Intent;
import android.support.design.internal.NavigationMenu;
import android.support.design.widget.CollapsingToolbarLayout;
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
 */

public class AboutActivity extends BaseActivity {

    @BindView(R.id.toolbar)  Toolbar mToolbar;
    @BindView(R.id.collapsing)  CollapsingToolbarLayout mCollapsing;
    @BindView(R.id.fab_dialog) FabSpeedDial mFab;
    @BindView(R.id.nestedScrollView)  NestedScrollView mNestedScrollView;

    public static void newInstance(Context context){
        context.startActivity(new Intent(context,AboutActivity.class));
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_about;
    }

    @Override
    protected void setUpView() {
        setBlur();
        setUpFab();
    }


    @Override
    protected void setUpData() {

    }

    /**
     * 初始化fab
     */
    private void setUpFab() {
        mFab.setMenuListener(new FabSpeedDial.MenuListener() {
            @Override//使用自定义的Menu
            public boolean onPrepareMenu(NavigationMenu navigationMenu) {
                return true;
            }

            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.back:
                        finish();
                        break;
                }
                return true;
            }

            @Override
            public void onMenuClosed() {

            }
        });
    }

    /**
     * 设置毛玻璃效果,用在这里比较难看
     */
    private void setBlur(){
        Glide.with(this).load(R.mipmap.about).bitmapTransform(new BlurTransformation(this,100))
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        mToolbar.setBackground(resource);
                    }
                });
    }
}
