package com.dingmouren.easymvp.ui.videos;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Build;

import com.dingmouren.easymvp.Constant;
import com.dingmouren.easymvp.MyApplication;
import com.dingmouren.easymvp.api.ApiManager;
import com.dingmouren.easymvp.bean.video.VideoBean;
import com.dingmouren.easymvp.bean.video.VideoCoverBean;
import com.dingzi.greendao.GankResultWelfareDao;
import com.dingzi.greendao.VideoCoverBeanDao;
import com.jiongbull.jlog.JLog;

import org.androidannotations.annotations.Background;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by dingmouren on 2017/1/11.
 */

public class VideoPresenter implements VideoContract.Presenter {
    private static final String TAG = VideoPresenter.class.getName();
    private VideoContract.View mView;
    private List<VideoBean> mList;
    ExecutorService fixedThreadPool = Executors.newFixedThreadPool(6);
    private long count;//用来记录从数据库取出指定项的数量
    private int page = 1;
    public VideoPresenter(VideoContract.View view) {
        this.mView = view;
        this.mList = mView.getVideoAdapterList();
    }

    @Override
    public void requestData() {
        ApiManager.getApiInstance().getVideoApiService().getVideoList(Constant.SHOW_API_APP_ID, Constant.SHOW_API_SIGN, "41", String.valueOf(page))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(showapiResBodyBeanVideoResult -> displayData(showapiResBodyBeanVideoResult.showapi_res_body.pagebean.contentlist), this::loadError);
    }

    private void loadError(Throwable throwable) {
        throwable.printStackTrace();
//        SnackbarUtils.showSimpleSnackbar(mRecycler,"网络不见了~~");
    }

    private void displayData(List<VideoBean> list) {
        for (int i = 0; i < list.size(); i++) {
            mList.add(list.get(i));
            storeCoverData(list.get(i).getVideo_uri());
        }
        mView.setData();
    }

    //往数据库中存储封面图的字节数组
    private void storeCoverData(String url) {
        fixedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                MediaMetadataRetriever mRetriever = new MediaMetadataRetriever();
                Bitmap bitmap = null;
                byte[] bytes = null;
                try {
                    if (Build.VERSION.SDK_INT >= 14) {
                        if (url.startsWith("http://") || url.startsWith("https://") || url.startsWith("widevine://")) {
                            mRetriever.setDataSource(url, new HashMap<String, String>());
                        } else {
                            mRetriever.setDataSource(url);
                        }
                    }
                    bitmap = mRetriever.getFrameAtTime(500, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                    bytes = byteArrayOutputStream.toByteArray();
                    count = MyApplication.getDaoSession().getVideoCoverBeanDao().queryBuilder().where(VideoCoverBeanDao.Properties.Url.eq(url)).count();
                    if (count == 0) {
                        MyApplication.getDaoSession().getVideoCoverBeanDao().insertOrReplaceInTx(new VideoCoverBean(url, bytes));
                    }
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } finally {
                    mRetriever.release();
                }
            }
        });
      }
}
