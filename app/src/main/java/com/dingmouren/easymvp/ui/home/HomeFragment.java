package com.dingmouren.easymvp.ui.home;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dingmouren.easymvp.R;
import com.dingmouren.easymvp.base.BaseFragment;
import com.dingmouren.easymvp.bean.GankContent;

import java.util.List;

import butterknife.BindView;

/**
 * Created by dingmouren on 2016/12/8.
 */

public class HomeFragment extends BaseFragment implements HomeContract.View {

    @BindView(R.id.recycler)  RecyclerView mRecycler;
    @BindView(R.id.relative_progressbar)  RelativeLayout mRelativeProgressbar;
    @BindView(R.id.tv_home_progress)  TextView mProgressTextView;
    @BindView(R.id.progressbar_home) ProgressBar mProgressbar;

    private HomePresenter mPresenter;
    private HomeAdapter mHomeAdapter;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_home;
    }

    @Override
    protected void setUpView() {
        initView();
    }

    @Override
    protected void setUpData() {
        initData();
    }

    private void initView() {
        mHomeAdapter = new HomeAdapter(getActivity());
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycler.setHasFixedSize(true);
    }





    private void initData() {
        mPresenter = new HomePresenter((HomeContract.View)this);
        mPresenter.requestData();
    }

    @Override
    public void setDataRefresh(boolean refresh) {
        if (refresh){
            showLoading(refresh);
        }else {
            showLoading(refresh);
        }
    }

    @Override
    public void setData(List<GankContent> list, String girlImgUrl) {
        mHomeAdapter.setData(list,girlImgUrl);
        mRecycler.setAdapter(mHomeAdapter);
    }

    @Override
    public RelativeLayout getProgressBarRelative() {
        return mRelativeProgressbar;
    }

    @Override
    public TextView getProgressBarTextView() {
        return mProgressTextView;
    }

    @Override
    public ProgressBar getProgressbar() {
        return mProgressbar;
    }

    /**
     * 刷新时的loading
     * @param refresh
     */
    private void showLoading(boolean refresh){
        if (refresh){
            mProgressbar.setIndeterminateDrawable(getResources().getDrawable(R.drawable.progressbar_circle));
            mProgressbar.setProgressDrawable(getResources().getDrawable(R.drawable.progressbar_circle));
            mRelativeProgressbar.setVisibility(View.VISIBLE);
            mProgressTextView.setText("正在拼命加载中。。。");
        }else {
            mRelativeProgressbar.setVisibility(View.GONE);
        }
    }

}
