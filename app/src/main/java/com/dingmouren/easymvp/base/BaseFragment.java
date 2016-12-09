package com.dingmouren.easymvp.base;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dingmouren.easymvp.ui.home.HomeContract;

import butterknife.ButterKnife;

/**
 * Created by dingmouren on 2016/12/5.
 */

public  abstract class BaseFragment extends Fragment {
    private View mContentView;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(setLayoutResourceID(),container,false);
        ButterKnife.bind(this,mContentView);
        mContext = getContext();

        init();//在初始化视图之前，做的一些操作
        setUpView();
        setUpData();
        return mContentView;
    }

    protected abstract int setLayoutResourceID();

    protected abstract void setUpView();

    protected abstract void setUpData();

    protected void init() {
    }

    public View getContentView() {
        return mContentView;
    }

    public Context getMContext() {
        return mContext;
    }
}
