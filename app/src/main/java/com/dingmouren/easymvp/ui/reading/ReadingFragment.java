package com.dingmouren.easymvp.ui.reading;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.dingmouren.easymvp.R;
import com.dingmouren.easymvp.base.BaseFragment;
import com.jiongbull.jlog.JLog;

import butterknife.BindView;

/**
 * Created by dingmouren on 2016/12/14.
 */

public class ReadingFragment extends BaseFragment {

    public static final String URL = "http://gank.io/xiandu";

    @BindView(R.id.swipe_refresh)  SwipeRefreshLayout mSwipeRefresh;
    @BindView(R.id.webview) WebView mWebView;

    private WebSettings mSettings;
    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_reading;
    }

    @Override
    protected void setUpView() {
        //SwipeRefresh相关
        if (mSwipeRefresh != null){
            mSwipeRefresh.setColorSchemeResources(R.color.main_color);//设置进度动画的颜色
            mSwipeRefresh.setProgressViewOffset(true,0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,24,getResources().getDisplayMetrics()));
            mSwipeRefresh.setOnRefreshListener(()-> new Handler().postDelayed(()-> mSwipeRefresh.setRefreshing(false),1500));
        }

        mSettings = mWebView.getSettings();

        mSettings.setJavaScriptEnabled(true);
        mSettings.setLoadsImagesAutomatically(true);
        mSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        mSettings.setDisplayZoomControls(true);
        mSettings.setSupportZoom(true);//支持缩放，默认是true
        mSettings.setBuiltInZoomControls(true);//设置内置的缩放控件，与上面联合使用
        mSettings.setDisplayZoomControls(false);//隐藏原声的缩放控件
        //自适应屏幕
        mSettings.setUseWideViewPort(true);//将图片调整到适合webview的大小
        mSettings.setLoadWithOverviewMode(true);//缩放到屏幕大小
        mSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//支持内容的重新布局
        mSettings.supportMultipleWindows();//支持多窗口

        mWebView.requestFocusFromTouch();//支持获取手势焦点，输入用户名、密码等
        mWebView.setWebViewClient(mWebViewClient);
        mWebView.setWebChromeClient(mWebChromeClient);

    }

    @Override
    protected void setUpData() {
        mWebView.loadUrl(URL);
    }

    /**
     *  WebViewClient 处理各种通知 & 请求事件
     */
    WebViewClient mWebViewClient = new WebViewClient(){
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            showLoading(true);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            showLoading(false);
        }


        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            showLoading(false);
            view.loadUrl("file:///android_asset/error.html");
        }


        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();    //表示等待证书响应
            // handler.cancel();      //表示挂起连接，为默认方式
            // handler.handleMessage(null);    //可做其他处理
        }


    };

    /**
     * WebChromeClient类 辅助 WebView 处理 Javascript 的对话框,网站图标,网站标题等等。
     */
    WebChromeClient mWebChromeClient = new WebChromeClient(){

        @Override//获得网页的加载进度并显示
        public void onProgressChanged(WebView view, int newProgress) {
        }

        @Override//获取Web页中的标题
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }
    };

    /**
     * 加载的loading
     * @param refresh
     */
    private void showLoading(boolean refresh){
        mSwipeRefresh.setRefreshing(refresh);
    }

    /**
     * 加载出错时，
     */
    private void showError(){
    }

    /**
     * 避免WebView内存溢出
     * activity销毁WebView,先加载null内容，再移除，接着销毁WebView,最后置空，
     */
    @Override
    public void onDestroy() {
        if (mWebView != null){
            mWebView.loadDataWithBaseURL(null,"","text/html","utf-8",null);
            mWebView.clearHistory();
            ((ViewGroup)mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }
}
