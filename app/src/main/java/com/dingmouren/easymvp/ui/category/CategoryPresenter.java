package com.dingmouren.easymvp.ui.category;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dingmouren.easymvp.api.ApiManager;
import com.dingmouren.easymvp.bean.GankContent;
import com.dingmouren.easymvp.util.SnackbarUtils;
import com.jiongbull.jlog.JLog;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by dingmouren on 2016/12/14.
 */

public class CategoryPresenter extends CategoryContract.Presenter{

    private CategoryContract.View mView;
    private RecyclerView mRecycler;
    private LinearLayoutManager mLinearLayoutManager;
    private String mStype;
    private List<GankContent> mList = new ArrayList<>();

    private int pageIndex = 1;
    private int mLastVisibleItem;
    private boolean isLoadMore = false;


    public CategoryPresenter(CategoryContract.View view) {
        this.mView = view;
        this.mRecycler = mView.getRecyclerView();
        this.mLinearLayoutManager = mView.getLayoutManager();
        this.mStype = mView.getType();
    }

    public void requestData(){
        if (isLoadMore) pageIndex++;
        ApiManager.getApiInstance().mApiService.getCategoryGankContent(mStype,pageIndex)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listGankResult -> parseData(listGankResult.results),this::loadError);

    }

    private void loadError(Throwable throwable){
        throwable.printStackTrace();
        mView.setRefresh(false);
        SnackbarUtils.showSimpleSnackbar(mRecycler,"网络不见了~~");
    }

    private void parseData(List<GankContent> list){
        if (null == list){
            return;
        }else {
            mList.addAll(list);
        }
        mView.setData(mList);
        mView.setRefresh(false);
    }

    public void addScrollListener(){
        mRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if ( newState == RecyclerView.SCROLL_STATE_IDLE){
                    if (mLinearLayoutManager.getItemCount() == 1) return;//只有一个条目直接返回
                    mLastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();//获取最后一个可见的条目的角标
                    //当上拉到最后一条时，请求下一页数据
                    if (mLastVisibleItem + 1 == mLinearLayoutManager.getItemCount()){
                        mView.setRefresh(true);
                        isLoadMore = true;
                        new Handler().postDelayed(() -> requestData(),1000);
                    }

                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mLastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();
            }
        });
    }
}
