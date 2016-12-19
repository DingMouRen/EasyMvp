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
    public List<GankContent> mList;
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
        mList = new ArrayList<>();
        mList.clear();
        if (null != results.getAndroid())
            mList.addAll(results.getAndroid());
        if (null != results.getiOS())
            mList.addAll(results.getiOS());
        if (null != results.get前端())
            mList.addAll(results.get前端());
        if (null != results.get拓展资源())
            mList.addAll(results.get拓展资源());
        if (null != results.get休息视频())
            mList.addAll(results.get休息视频());
        mView.setData(mList, results.get福利().get(0).getUrl());
        mView.setDataRefresh(false);

    }
}
