package com.dingmouren.easymvp.ui.webdetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.dingmouren.easymvp.R;
import com.dingmouren.easymvp.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by dingzi on 2016/12/9.
 */

public class WebDetailActivity extends BaseActivity {

    private static final String URL = "url";
    private static final String DESC = "desc";
    @BindView(R.id.toolbar)  Toolbar mToolbar;
    @BindView(R.id.webview)  WebView mWebView;
    @BindView(R.id.tv_title_video)  TextView mTitle;
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
        mSettings = mWebView.getSettings();
        mSettings.setJavaScriptEnabled(true);
        mSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.loadUrl(mUrl);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWebView.loadUrl(mUrl);
    }
}
