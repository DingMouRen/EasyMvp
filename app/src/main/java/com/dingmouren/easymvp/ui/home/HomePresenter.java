package com.dingmouren.easymvp.ui.home;

import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.dingmouren.easymvp.api.ApiManager;
import com.dingmouren.easymvp.bean.GankContent;
import com.dingmouren.easymvp.bean.GankResultCategory;
import com.dingmouren.easymvp.util.HtmlFormat;
import com.dingmouren.easymvp.util.SnackbarUtils;
import com.jiongbull.jlog.JLog;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by dingmouren on 2016/12/8.
 */

public class HomePresenter extends HomeContract.Presenter {

    private static final String TAG = HomePresenter.class.getName();
    private HomeContract.View mView;
    private String mDate;
    public String mImgUrl;

    public HomePresenter(HomeContract.View view){
        this.mView = view;
    }

    public void requestData(){
        ApiManager.getApiInstance().mApiService.getGankDatePushed()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listGankResult -> getData(listGankResult.results.get(0)),this :: loadError);
    }

    private void loadError(Throwable throwable){
        throwable.printStackTrace();
        mView.setDataRefresh(false);
    }

    private void getData(String date){
        mDate =date.replace('-', '/');
        JLog.e("mDate------",mDate);
        if (mDate != null) {
            ApiManager.getApiInstance().mApiService.getGankDay(mDate)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(gankResultCategoryGankResult -> parseData(gankResultCategoryGankResult.results), this::loadError);
        }
    }

    private void parseData(GankResultCategory results) {
        List<GankContent> list = new ArrayList<>();
        list.clear();
        list.addAll(results.getAndroid());
        list.addAll(results.getiOS());
        list.addAll(results.get前端());
        list.addAll(results.get拓展资源());
        list.addAll(results.get休息视频());
        mView.setData(list,results.get福利().get(0).getUrl());
        mView.setDataRefresh(false);

    }
}
