package com.dingmouren.easymvp.ui.picture;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.internal.NavigationMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dingmouren.easymvp.R;
import com.dingmouren.easymvp.base.BaseActivity;
import com.dingmouren.easymvp.util.NetworkUtil;
import com.dingmouren.easymvp.util.SnackbarUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import io.github.yavski.fabspeeddial.FabSpeedDial;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by dingzi on 2016/12/5.
 */

public class PictureActivity extends BaseActivity   {
    private static final String TAG = PictureActivity.class.getName();
    public static final String IMG_URL = "img_url";
    public static final String IMG_ID = "img_Id";

    private static final String SAVED_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/EasyMvp";
    private String imgId;
    @BindView(R.id.img_picture)  ImageView img;
    @BindView(R.id.fab_dialog)  FabSpeedDial mFabDialog;
    @BindView(R.id.tv_no_network) TextView mTvNetNotice;

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
    protected void init(Bundle savedInstanceState) {
        imgId = getIntent().getStringExtra(IMG_ID);
    }

    @Override
    protected void setUpView() {
        initFabDialog();
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }

    @Override
    protected void setUpData() {
        String imgurl = getIntent().getStringExtra(IMG_URL);
        Glide.with(this).load(imgurl).centerCrop().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(img);
        if (!NetworkUtil.isAvailable(this)){
            mTvNetNotice.setVisibility(View.VISIBLE);
        }else {
            mTvNetNotice.setVisibility(View.GONE);
        }
        new PhotoViewAttacher(img);
    }

    /**
     * 初始化fab
     */
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
     * 保存图片
     */
    private void saveImage() {
        Bitmap bmp = ((BitmapDrawable)img.getDrawable()).getBitmap();
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "EasyMvp");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            Toast.makeText(this,"图片保存在"+appDir.getAbsolutePath(),Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(this.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + appDir.getAbsolutePath())));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Glide.clear(img);
    }
}
