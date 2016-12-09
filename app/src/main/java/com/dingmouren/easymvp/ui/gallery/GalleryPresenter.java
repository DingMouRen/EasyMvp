package com.dingmouren.easymvp.ui.gallery;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dingmouren.easymvp.api.ApiManager;
import com.dingmouren.easymvp.bean.GankResultWelfare;
import com.dingmouren.easymvp.util.SnackbarUtils;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by dingmouren on 2016/12/3.
 */

public class GalleryPresenter extends GalleryContract.Presenter<GalleryContract.View> {

    public GalleryContract.View mGalleryView;
    public RecyclerView mRecycler;
    public GalleryAdapter mGalleryAdapter;
    public LinearLayoutManager mLinearLayoutManager;

    private int page = 1;
    private boolean isLoadMore = false;
    private int mLastVisibleItem;
    public List<GankResultWelfare> mList = new ArrayList<>();
    private int mLastPos = -1;
    private Runnable mBlurRunnable;

    public GalleryPresenter(GalleryContract.View view){
        this.mGalleryView = view;
        this.mRecycler = view.getRecyclerView();
        this.mGalleryAdapter = view.getGalleryAdapter();
        mLinearLayoutManager = view.getLayoutManager();
    }

    //请求数据
    public void requestData(){
        if (isLoadMore) page = page + 1;
        ApiManager.getApiInstance().mApiService.getGirlPics(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(listGankResult -> displayData(listGankResult.results),this :: loadError);
    }

    /**
     * 没请求到数据
     * @param throwable
     */
    private void loadError(Throwable throwable){
        throwable.printStackTrace();
        SnackbarUtils.showSimpleSnackbar(mRecycler,"网络不见了~~");
    }

    /**
     * 展示相册图片
     * @param list
     */
    private void displayData(List<GankResultWelfare> list){
        mGalleryView.setDataRefresh(true);
        if (list == null){
            return;
        }else {
            mList.addAll(list);
        }
        mGalleryAdapter.setList(mList);
        mGalleryAdapter.notifyDataSetChanged();
        mGalleryView.setDataRefresh(false);
    }

    /**
     * recyclerview的监听，加载到最后一个条目时，请求下一页数据
     */
    public void addScrollistener(){
        mRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
               if (newState == RecyclerView.SCROLL_STATE_IDLE){
                   //获取最后一个可见条目的角标
                   mLastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();
                   //当显示本页最后一个条目时，加载下一页
                   if (mLastVisibleItem + 1 == mLinearLayoutManager.getItemCount()){
                       mGalleryView.setDataRefresh(true);
                       isLoadMore = true;
                       new Handler().postDelayed(()-> requestData(),1000);
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
