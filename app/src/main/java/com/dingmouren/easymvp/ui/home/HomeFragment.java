package com.dingmouren.easymvp.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.dingmouren.easymvp.R;
import com.dingmouren.easymvp.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dingmouren on 2016/12/8.
 */

public class HomeFragment extends BaseFragment implements HomeContract.View {

    @BindView(R.id.swipe_refresh)  SwipeRefreshLayout mSwipeRefresh;
    @BindView(R.id.webview)  WebView mWebView;


    private HomePresenter mPresenter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home,container,false);
        ButterKnife.bind(this,rootView);
        return rootView;
    }

    @Override
    protected int setLayoutResourceID() {
        return 0;
    }

    @Override
    protected void setUpView() {

    }

    @Override
    protected void setUpData() {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initSwipeReferesh();
        initData();
    }

    private void initSwipeReferesh() {
        if (mSwipeRefresh != null) {
            mSwipeRefresh.setColorSchemeResources(R.color.main_color);//设置进度动画的颜色
//            mSwipeRefresh.setProgressBackgroundColorSchemeResource(android.R.color.holo_blue_bright);//设置进度圈背景颜色
            //这里进行单位换算  第一个参数是单位，第二个参数是单位数值，这里最终返回的是24dp对相应的px值
            mSwipeRefresh.setProgressViewOffset(true, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
            mSwipeRefresh.setOnRefreshListener(() -> mPresenter.requestData());
        }
    }


    private void initData() {
        mSwipeRefresh.setRefreshing(true);
        mPresenter = new HomePresenter((HomeContract.View)this);
        mPresenter.requestData();
    }

    @Override
    public void setDataRefresh(boolean refresh) {
        if (refresh){
            mSwipeRefresh.setRefreshing(true);
        }else {
            mSwipeRefresh.setRefreshing(false);
        }
    }

    @Override
    public WebView getWebView() {
        return mWebView;
    }
}
