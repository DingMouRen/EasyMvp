package com.dingmouren.easymvp.ui.home;

import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.dingmouren.easymvp.api.ApiManager;
import com.dingmouren.easymvp.util.HtmlFormat;
import com.jiongbull.jlog.JLog;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by dingmouren on 2016/12/8.
 */

public class HomePresenter extends HomeContract.Presenter {

    private static final String TAG = HomePresenter.class.getName();
    private HomeContract.View mView;
    private WebView mWebView;
    private WebSettings mSettings;

    public HomePresenter(HomeContract.View view){
        this.mView = view;
        mWebView = mView.getWebView();
        initWebView();
    }

    private void initWebView() {
        mSettings = mWebView.getSettings();
        mSettings.setJavaScriptEnabled(true);
        mSettings.setLoadWithOverviewMode(true);
        mSettings.setAppCacheEnabled(true);
        mSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mSettings.setSupportZoom(true);
        mSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);//提高渲染优先级
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient(new WebViewClient());
    }
    public void requestData(){
        ApiManager.getApiInstance().mApiService.getDataUpToDate()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(gankDataUpToDateGankResult -> displayData(gankDataUpToDateGankResult.results.get(0).getContent()),this :: loadError);
    }

    private void loadError(Throwable throwable){
        throwable.printStackTrace();
        mView.setDataRefresh(false);
    }

    private void displayData(String mHtml){
        String data = "<!DOCTYPE html><html><head><meta charset=\"UTF-8\"><meta name=\"viewport\" content=\"width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no\"></head><body>" + mHtml + "</body></html>";
        mWebView.loadDataWithBaseURL(null,HtmlFormat.formatHtmlSource(data),"text/html","UTF-8",null);//这个方法可以解决乱码问题
        JLog.e(TAG,data);
        mView.setDataRefresh(false);
    }
}
