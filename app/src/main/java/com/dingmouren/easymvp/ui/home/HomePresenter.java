package com.dingmouren.easymvp.ui.home;

import android.os.Build;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dingmouren.easymvp.R;
import com.dingmouren.easymvp.api.ApiManager;
import com.dingmouren.easymvp.bean.GankContent;
import com.dingmouren.easymvp.bean.GankResultCategory;
import com.dingmouren.easymvp.ui.home.layouts.ImgHome;
import com.jiongbull.jlog.JLog;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by dingmouren on 2016/12/8.
 */

public class HomePresenter implements HomeContract.Presenter {

    private static final String TAG = HomePresenter.class.getName();
    private HomeContract.View mView;
    private String mDate;
    private RelativeLayout mRelativeProgressbar;
    private TextView mProgressTextView;
    private ProgressBar mProgressbar;
    public List<Object> mItems;
    //点击重新请求数据
    private View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            requestData();
        }
    };

    public HomePresenter(HomeContract.View view) {
        this.mView = view;
        mRelativeProgressbar = mView.getProgressBarRelative();
        mProgressTextView = mView.getProgressBarTextView();
        mProgressbar = mView.getProgressbar();
        mItems = mView.getItems();
        mRelativeProgressbar.setOnClickListener(mListener);
    }

    /**
     * 请求数据，请求的是发布文章的日期集合，获取最新的日期
     */
    @Override
    public void requestData() {
        mView.setDataRefresh(true);
        ApiManager.getApiInstance().mApiService.getGankDatePushed()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listGankResult -> getData(listGankResult.results.get(0)), this::loadError);
    }

    /**
     * 异常处理
     *
     * @param throwable
     */
    @Override
    public void loadError(Throwable throwable) {
        throwable.printStackTrace();
        new Handler().postDelayed(() -> {
            mRelativeProgressbar.setVisibility(View.VISIBLE);
            mProgressbar.setIndeterminateDrawable(mProgressbar.getContext().getResources().getDrawable(R.mipmap.loading_error));
            mProgressbar.setProgressDrawable(mProgressbar.getContext().getResources().getDrawable(R.mipmap.loading_error));
            mProgressTextView.setText("这里空空如也，点击刷新哟~~");
        }, 3000);

    }

    /**
     * 对获取的最新日期进行解析，去请求这个日期的数据
     *
     * @param date
     */
    private void getData(String date) {
        mDate = date.replace('-', '/');
        JLog.e("mDate------", mDate);
        if (mDate != null) {
            ApiManager.getApiInstance().mApiService.getGankDay(mDate)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(gankResultCategoryGankResult -> parseData(gankResultCategoryGankResult.results), this::loadError);
        }
    }

    private void parseData(GankResultCategory results) {
        mItems.clear();

        if (null != results.get福利().get(0).getUrl())
            mItems.add(new ImgHome(results.get福利().get(0).getUrl()));

        if (null != results.getAndroid()) {
            for (int i = 0; i < results.getAndroid().size() ; i++) {
                mItems.add(results.getAndroid().get(i));
            }
        }
        if (null != results.getiOS()){
            for (int i = 0; i < results.getiOS().size() ; i++) {
                mItems.add(results.getiOS().get(i));
            }
        }

        if (null != results.get前端()){
            for (int i = 0; i < results.get前端().size() ; i++) {
                mItems.add(results.get前端().get(i));
            }
        }

        if (null != results.get拓展资源()){
            for (int i = 0; i < results.get拓展资源().size() ; i++) {
                mItems.add(results.get拓展资源().get(i));
            }
        }
        if (null != results.get休息视频()){
            for (int i = 0; i < results.get休息视频().size() ; i++) {
                mItems.add(results.get休息视频().get(i));
            }
        }
        mView.notifyDataSetChanged();

    }
}
