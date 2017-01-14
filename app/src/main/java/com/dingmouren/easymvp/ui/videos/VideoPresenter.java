package com.dingmouren.easymvp.ui.videos;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.AbsListView;

import com.dingmouren.easymvp.Constant;
import com.dingmouren.easymvp.MyApplication;
import com.dingmouren.easymvp.R;
import com.dingmouren.easymvp.api.ApiManager;
import com.dingmouren.easymvp.bean.video.VideoBean;
import com.dingmouren.easymvp.bean.video.VideoCoverBean;
import com.dingmouren.easymvp.util.NetworkUtil;
import com.dingmouren.easymvp.util.SnackbarUtils;
import com.dingzi.greendao.VideoCoverBeanDao;


import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by dingmouren on 2017/1/11.
 */

public class VideoPresenter implements VideoContract.Presenter {
    private static final String TAG = VideoPresenter.class.getName();
    private VideoContract.View mView;
    private List<VideoBean> mList;
    private RecyclerView mRecycler;
    private LinearLayoutManager mLinearLayoutManager;
    private int mLastVisibleItem;
    private boolean isLoadMore = false;
    ExecutorService fixedThreadPool = Executors.newFixedThreadPool(6);
    private long count;//用来记录从数据库取出指定项的数量
    private int page = 1;

    public VideoPresenter(VideoContract.View view) {
        this.mView = view;
        this.mList = mView.getVideoAdapterList();
        this.mRecycler = mView.getRecyclerView();
        this.mLinearLayoutManager = mView.getLayoutManager();
    }

    @Override
    public void requestData() {
        if (mView.isRefreshingg() && !isLoadMore && NetworkUtil.isAvailable(mRecycler.getContext())) {//这里加了一个对网络状态的判断，防止了一个bug的发生，情景是下拉刷新时滑动recyclerview崩溃的异常，
            mList.clear();//请求第一页时，将之前列表显示数据清空
            page = 1;
        } else if (!mView.isRefreshingg() && isLoadMore) {
            page = page + 1;
        }
        ApiManager.getApiInstance().getVideoApiService().getVideoList(Constant.SHOW_API_APP_ID, Constant.SHOW_API_SIGN, "41", String.valueOf(page))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(showapiResBodyBeanVideoResult -> displayData(showapiResBodyBeanVideoResult.showapi_res_body.pagebean.contentlist), this::loadError);
    }

    private void loadError(Throwable throwable) {
        throwable.printStackTrace();
        page = 1;//不管是不是加载了数据库的数据，都从第一页开始加载
        mView.setRefresh(false);
        mView.loadMore(false);
        SnackbarUtils.showSimpleSnackbar(mRecycler, "网络不见了~~");
    }

    private void displayData(List<VideoBean> list) {
        for (int i = 0; i < list.size(); i++) {
            mList.add(list.get(i));
            storeCoverData(list.get(i).getVideo_uri());
        }
        mView.notifyDataSetChanged();
        isLoadMore = false;

    }

    /**
     * 往数据库中存储封面图的字节数组
     */
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
                    bitmap = mRetriever.getFrameAtTime(1000, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 10, byteArrayOutputStream);
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

    /**
     * 滑动到底部自动加载下一页
     */
    public void addScrollListener() {
        mRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (mLinearLayoutManager.getItemCount() == 1) return;//只有一个条目直接返回
                    mLastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();//获取最后一个可见的条目的角标
                    //当上拉到最后一条时，请求下一页数据
                    if (mLastVisibleItem + 1 == mLinearLayoutManager.getItemCount()) {
                        mView.loadMore(true);
                        isLoadMore = true;
                        new Handler().postDelayed(() -> requestData(), 1000);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mLastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();
            }
        });

    }

}



