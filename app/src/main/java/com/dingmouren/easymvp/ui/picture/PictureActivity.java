package com.dingmouren.easymvp.ui.picture;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dingmouren.easymvp.R;
import com.dingmouren.easymvp.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by dingzi on 2016/12/5.
 */

public class PictureActivity extends BaseActivity {

    public static final String IMG_URL = "img_url";
    @BindView(R.id.img_picture)
    ImageView img;
    public static Intent newInstance(Context  context,String imgUrl){
        Intent intent = new Intent(context,PictureActivity.class);
        intent.putExtra(IMG_URL,imgUrl);
        context.startActivity(intent);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        ButterKnife.bind(this);
        initPicture();
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }

    private void initPicture() {
        String imgurl = getIntent().getStringExtra(IMG_URL);
        Glide.with(this).load(imgurl).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).into(img);
        new PhotoViewAttacher(img);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Glide.clear(img);
    }
}
