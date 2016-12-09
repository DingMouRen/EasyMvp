package com.dingmouren.easymvp.ui.picture;

import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dingmouren.easymvp.api.ApiManager;
import com.dingmouren.easymvp.bean.GankResultWelfare;
import com.dingmouren.easymvp.util.SnackbarUtils;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by dingmouren on 2016/12/1.
 */

public class WelfarePresenter extends WelfareContract.Presenter<WelfareContract.View> {

    public WelfareContract.View mHomeView;
    public GridLayoutManager mGridLayoutManager;
    public RecyclerView mRecycler;
    public WelfareAdapter mWelfareAdapter;

    private List<GankResultWelfare> mList = new ArrayList<>();
    private int mPage = 1;
    private int mLastVisibleItem;
    private boolean isLoadMore = false;//是否加载更多

    public WelfarePresenter(WelfareContract.View view) {
        this.mHomeView = view;
        mGridLayoutManager = mHomeView.getLayoutManager();
        mRecycler = mHomeView.getRecyclerView();
        mWelfareAdapter = mHomeView.getHomeAdapter();
    }

    /**
     * 请求数据
     */
    public void requestData(){
        if (isLoadMore) mPage = mPage + 1;
        ApiManager.getApiInstance().mApiService.getGirlPics(mPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listGankResult -> displayData(listGankResult.results),this :: loadError);
    }

    private void loadError(Throwable throwable){
        throwable.printStackTrace();
        mHomeView.setDataRefresh(false);
        SnackbarUtils.showSimpleSnackbar(mRecycler,"网络不见了~~");
    }
    /**
     * 显示获取的数据
     * @param list
     */
    public void displayData(List<GankResultWelfare> list){
            if (list == null){
                mHomeView.setDataRefresh(false);
                return;
            }else {
                mList.addAll(list);
            }
            mWelfareAdapter.setList(mList);
            mWelfareAdapter.notifyDataSetChanged();
        mHomeView.setDataRefresh(false);
    }

    public void addScrollListener(){
        mRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
               if ( newState == RecyclerView.SCROLL_STATE_IDLE){
                   if (mGridLayoutManager.getItemCount() == 1) return;//只有一个条目直接返回
                   mLastVisibleItem = mGridLayoutManager.findLastVisibleItemPosition();//获取最后一个可见的条目的角标
                   //当上拉到最后一条时，请求下一页数据
                   if (mLastVisibleItem + 1 == mGridLayoutManager.getItemCount()){
                       mHomeView.setDataRefresh(true);
                       isLoadMore = true;
                       new Handler().postDelayed(() -> requestData(),1000);
                   }

               }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mLastVisibleItem = mGridLayoutManager.findLastVisibleItemPosition();
            }
        });
    }


}
