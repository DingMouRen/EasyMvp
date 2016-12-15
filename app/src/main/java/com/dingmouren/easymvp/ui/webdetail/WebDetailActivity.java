package com.dingmouren.easymvp.ui.webdetail;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dingmouren.easymvp.R;
import com.dingmouren.easymvp.base.BaseActivity;
import com.dingmouren.easymvp.util.NetworkUtil;
import com.jiongbull.jlog.JLog;

import butterknife.BindView;

/**
 * Created by dingzi on 2016/12/9.
 */

public class WebDetailActivity extends BaseActivity {

    String cacheDirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/webviewcache";
    private static final String URL = "url";
    private static final String DESC = "desc";

    @BindView(R.id.toolbar)  Toolbar mToolbar;
    @BindView(R.id.webview)  WebView mWebView;
    @BindView(R.id.tv_title_video)  TextView mTitle;
    @BindView(R.id.progressbar_webviewdetail)  ProgressBar mProgressBar;
    private String mUrl,mDesc ;
    private WebSettings mSettings;

    public static void newInstance(Context context,String url,String desc){
        Intent intent = new Intent(context,WebDetailActivity.class);
        intent.putExtra(URL,url);
        intent.putExtra(DESC,desc);
        context.startActivity(intent);

    }
    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_webview;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mUrl = getIntent().getStringExtra(URL);
        mDesc = getIntent().getStringExtra(DESC);
    }

    @Override
    protected void setUpView() {
        mTitle.setText(mDesc);
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.mipmap.arrow_back);
        mToolbar.setNavigationOnClickListener((view) -> finish());

    }

    @Override
    protected void setUpData() {

        initWebViewSettings();

        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient(mWebViewClient);//使得打开网页时不调用系统浏览器， 而是在本WebView中显示,处理各种通知 & 请求事件
        mWebView.loadUrl(mUrl);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWebView.onResume();
    }

    /**
     * 初始化WebViewSettings
     */
    private void initWebViewSettings(){
        mSettings = mWebView.getSettings();
        mSettings.setJavaScriptEnabled(true);//访问的页面与js交互，设置为true，表示支持js
        mSettings.setJavaScriptCanOpenWindowsAutomatically(true);//支持通过js打开新窗口
        mSettings.setLoadsImagesAutomatically(true);//支持自动加载图片
        mSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        mSettings.setUseWideViewPort(true);//将图片调整到适合webview的大小
        mSettings.setLoadWithOverviewMode(true);//缩放到屏幕大小，与上面一个合用
        //支持缩放操作
        mSettings.setSupportZoom(true);
        mSettings.setBuiltInZoomControls(true);//设置内置的缩放控件，为false，该webview不可缩放
        mSettings.setDisplayZoomControls(false);//隐藏原生的缩放控件
        if (NetworkUtil.isAvailable(this)) {
            mSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);//有网就从网络获取最新的数据，
        } else {
            mSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//没网，则从本地获取，即离线加载
        }
//        mSettings.setDomStorageEnabled(true); // 开启 DOM storage API 功能  开启这个显示不了gank的视频html
        mSettings.setDatabaseEnabled(true);   //开启 database storage API 功能
        mSettings.setAppCacheEnabled(true);//开启 Application Caches 功能
        mSettings.setAppCachePath(cacheDirPath); //设置  Application Caches 缓存目录,每个 Application 只调用一次 WebSettings.setAppCachePath()，WebSettings.setAppCacheMaxSize()
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
            JLog.e("onReceivedError");
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
        if (refresh){
            mProgressBar.setIndeterminateDrawable(getResources().getDrawable(R.drawable.progressbar_circle));
            mProgressBar.setProgressDrawable(getResources().getDrawable(R.drawable.progressbar_circle));
            mProgressBar.setVisibility(View.VISIBLE);
        }else {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    /**
     * 加载出错时，
     */
    private void showError(){
        mProgressBar.setIndeterminateDrawable( getResources().getDrawable(R.mipmap.loading_error));
        mProgressBar.setProgressDrawable(getResources().getDrawable(R.mipmap.loading_error));
    }

    /**
     * 避免WebView内存溢出
     * activity销毁WebView,先加载null内容，再移除，接着销毁WebView,最后置空，
     */
    @Override
    protected void onDestroy() {
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
//-------WebChromeClient-----------------------
//        //获取Web页中的title用来设置自己界面中的title
//        //当加载出错的时候，比如无网络，这时onReceiveTitle中获取的标题为 找不到该网页,
//        //因此建议当触发onReceiveError时，不要使用获取到的title
//        @Override
//        public void onReceivedTitle(WebView view, String title) {
//            MainActivity.this.setTitle(title);
//        }
//
//        @Override
//        public void onReceivedIcon(WebView view, Bitmap icon) {
//            //
//        }
//
//        @Override
//        public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
//            //
//            return true;
//        }
//
//        @Override
//        public void onCloseWindow(WebView window) {
//        }
//
//        //处理alert弹出框，html 弹框的一种方式
//        @Override
//        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
//            //
//            return true;
//        }
//
//        //处理confirm弹出框
//        @Override
//        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult
//                result) {
//            //
//            return true;
//        }
//
//        //处理prompt弹出框
//        @Override
//        public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
//            //
//            return true;
//        }


//-----------------WebViewClient-----------------------
//        shouldOverrideUrlLoading(WebView view, String url)  最常用的，比如上面的。
//        //在网页上的所有加载都经过这个方法,这个函数我们可以做很多操作。
//        //比如获取url，查看url.contains(“add”)，进行添加操作
//
//        shouldOverrideKeyEvent(WebView view, KeyEvent event)
//        //重写此方法才能够处理在浏览器中的按键事件。
//
//        onPageStarted(WebView view, String url, Bitmap favicon)
//        //这个事件就是开始载入页面调用的，我们可以设定一个loading的页面，告诉用户程序在等待网络响应。
//
//        onPageFinished(WebView view, String url)
//        //在页面加载结束时调用。同样道理，我们可以关闭loading 条，切换程序动作。
//
//        onLoadResource(WebView view, String url)
//        // 在加载页面资源时会调用，每一个资源（比如图片）的加载都会调用一次。
//
//        onReceivedError(WebView view, int errorCode, String description, String failingUrl)
//        // (报告错误信息)
//
//        doUpdateVisitedHistory(WebView view, String url, boolean isReload)
//        //(更新历史记录)
//
//        onFormResubmission(WebView view, Message dontResend, Message resend)
//        //(应用程序重新请求网页数据)
//
//        onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host,String realm)
//        //（获取返回信息授权请求）
//
//        onReceivedSslError(WebView view, SslErrorHandler handler, SslError error)
//        //重写此方法可以让webview处理https请求。
//
//        onScaleChanged(WebView view, float oldScale, float newScale)
//        // (WebView发生改变时调用)
//
//        onUnhandledKeyEvent(WebView view, KeyEvent event)
//        //（Key事件未被加载时调用）
