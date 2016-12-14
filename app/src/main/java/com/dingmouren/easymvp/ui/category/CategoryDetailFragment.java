package com.dingmouren.easymvp.ui.category;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;

import com.dingmouren.easymvp.R;
import com.dingmouren.easymvp.base.BaseFragment;
import com.dingmouren.easymvp.bean.GankContent;


import java.util.List;

import butterknife.BindView;

/**
 * Created by dingmouren on 2016/12/13.
 */

public class CategoryDetailFragment extends BaseFragment implements CategoryContract.View {

    @BindView(R.id.swipe_refresh)  SwipeRefreshLayout mSwipeRefresh;
    @BindView(R.id.recycler) RecyclerView mRecycler;

    private String mType;
    private LinearLayoutManager mLinearLayoutManager;
    private CategoryAdapter mAdapter;
    private CategoryPresenter mPresenter;
    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_category_detail;
    }

    @Override
    protected void init() {
        mType = getArguments().getString("type");
        mAdapter = new CategoryAdapter(getActivity());

    }

    @Override
    protected void setUpView() {
        //SwipeRefresh相关
        if (mSwipeRefresh != null){
            mSwipeRefresh.setColorSchemeResources(R.color.main_color);//设置进度动画的颜色
            mSwipeRefresh.setProgressViewOffset(true,0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,24,getResources().getDisplayMetrics()));
            mSwipeRefresh.setOnRefreshListener(()->mPresenter.requestData());
        }

        //RecyclerView相关
        mLinearLayoutManager = new LinearLayoutManager(mRecycler.getContext());
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(mLinearLayoutManager);
        mRecycler.setAdapter(mAdapter);
        //Presenter相关
        mPresenter = new CategoryPresenter((CategoryContract.View)this);

    }

    @Override
    protected void setUpData() {
        setRefresh(true);
        mPresenter.requestData();
        mPresenter.addScrollListener();//滚动的监听
    }


    @Override
    public void setRefresh(boolean refresh) {
        if (refresh){
            mSwipeRefresh.setRefreshing(true);
        }else {
            mSwipeRefresh.setRefreshing(false);
        }
    }


    @Override
    public RecyclerView getRecyclerView() {
        return mRecycler;
    }

    @Override
    public LinearLayoutManager getLayoutManager() {
        return mLinearLayoutManager;
    }

    @Override
    public String getType() {
        return mType;
    }

    @Override
    public void setData(List<GankContent> list) {
        mAdapter.setList(list);
        mAdapter.notifyDataSetChanged();
    }


}
