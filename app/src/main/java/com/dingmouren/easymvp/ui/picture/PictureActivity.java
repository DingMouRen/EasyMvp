package com.dingmouren.easymvp.ui.picture;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.internal.NavigationMenu;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dingmouren.easymvp.R;
import com.dingmouren.easymvp.base.BaseActivity;
import com.dingmouren.easymvp.util.SnackbarUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.FabSpeedDialBehaviour;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by dingzi on 2016/12/5.
 */

public class PictureActivity extends BaseActivity   {

    public static final String IMG_URL = "img_url";
    public static final String IMG_ID = "img_Id";

    private static final String SAVED_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/EasyMvp";
    private String imgId;
    @BindView(R.id.img_picture)  ImageView img;
    @BindView(R.id.fab_dialog)  FabSpeedDial mFabDialog;

    public static Intent newInstance(Context  context,String imgUrl,String imgId){
        Intent intent = new Intent(context,PictureActivity.class);
        intent.putExtra(IMG_URL,imgUrl);
        intent.putExtra(IMG_ID,imgId);
        context.startActivity(intent);
        return intent;
    }


    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_picture;
    }

    @Override
    protected void setUpView() {
        initPicture();
        initFabDialog();
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }

    @Override
    protected void setUpData() {

    }


    private void initPicture() {
        String imgurl = getIntent().getStringExtra(IMG_URL);
        imgId = getIntent().getStringExtra(IMG_ID);
        Glide.with(this).load(imgurl).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).into(img);
        new PhotoViewAttacher(img);
    }

    private void initFabDialog() {
        mFabDialog.setMenuListener(new FabSpeedDial.MenuListener() {
            @Override
            public boolean onPrepareMenu(NavigationMenu navigationMenu) {
                return true;
            }

            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.back:
                        finish();
                        break;
                    case R.id.img_save:
                        saveImage();
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
     * 保存图片到
     */
    private void saveImage() {
        SnackbarUtils.showSimpleSnackbar(img,"图片保存在"+ SAVED_PATH);
        img.buildDrawingCache();
        Bitmap bitmap = img.getDrawingCache();
        //将bitmap转换成二进制，写入本地
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray = stream.toByteArray();
        File dir = new File(SAVED_PATH);
        if (!dir.exists()){
            dir.mkdir();
        }
        File file = new File(dir,"girl_"+imgId + ".png");

        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(byteArray,0,byteArray.length);
            fos.flush();
            //使用广播通知系统相册进行更新相册
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(file);
            intent.setData(uri);
            PictureActivity.this.sendBroadcast(intent);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Glide.clear(img);
    }
}
