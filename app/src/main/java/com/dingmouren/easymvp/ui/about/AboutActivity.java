package com.dingmouren.easymvp.ui.about;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.dingmouren.easymvp.R;
import com.dingmouren.easymvp.base.BaseActivity;

import butterknife.BindView;
import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by dingmouren on 2016/12/10.
 */

public class AboutActivity extends BaseActivity {

    @BindView(R.id.toolbar)  Toolbar mToolbar;
    @BindView(R.id.collapsing)
    CollapsingToolbarLayout mCollapsing;

    public static void newInstance(Context context){
        context.startActivity(new Intent(context,AboutActivity.class));
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_about;
    }

    @Override
    protected void setUpView() {
    }

    @Override
    protected void setUpData() {

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
        Glide.with(this).load(R.mipmap.about).bitmapTransform(new BlurTransformation(this,100))
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        mCollapsing.setContentScrim(resource);
                    }
                });
    }
}
