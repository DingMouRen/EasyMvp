package com.dingmouren.easymvp.ui.picture;

import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dingmouren.easymvp.MyApplication;
import com.dingmouren.easymvp.api.ApiManager;
import com.dingmouren.easymvp.bean.GankResult;
import com.dingmouren.easymvp.bean.GankResultWelfare;
import com.dingmouren.easymvp.util.SnackbarUtils;
import com.dingzi.greendao.GankResultWelfareDao;
import com.jiongbull.jlog.JLog;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by dingmouren on 2016/12/1.
 */

public class WelfarePresenter implements WelfareContract.Presenter<WelfareContract.View> {

    public static final String TAG = WelfarePresenter.class.getName();

    public WelfareContract.View mView;
    public GridLayoutManager mGridLayoutManager;
    public RecyclerView mRecycler;

    private List<GankResultWelfare> mList = new ArrayList<>();
    private int mPage = 1;
    private int mLastVisibleItem;
    private boolean isLoadMore = false;//是否加载更多
    private List<Object> mItems;

    public WelfarePresenter(WelfareContract.View view) {
        this.mView = view;
        mGridLayoutManager = mView.getLayoutManager();
        mRecycler = mView.getRecyclerView();
        mItems = mView.getItems();
    }

    /**
     * 请求数据
     */
    public void requestData() {
        if (mView.isRefreshing()){
            mItems.clear();//请求第一页时，将之前列表显示数据清空
            mPage = 1;
        }else if (!mView.isRefreshing() && isLoadMore) {
            mPage = mPage + 1;
        }
        ApiManager.getApiInstance().mApiService.getGirlPics(mPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listGankResult -> displayData(listGankResult.results), this::loadError);
    }

    private void loadError(Throwable throwable) {
        throwable.printStackTrace();
        mPage = 1;//不管是不是加载了数据库的数据，都从第一页开始加载
        mView.setDataRefresh(false);
        mView.loadMore(false);
        SnackbarUtils.showSimpleSnackbar(mRecycler, "网络不见了~~");
    }

    /**
     * 显示获取的数据
     *
     * @param list
     */
    public void displayData(List<GankResultWelfare> list) {
        for (int i = 0; i < list.size(); i++) {
            mItems.add(list.get(i));
        }
        mView.notifyDataSetChanged();
        //将数据插入数据库
        Observable.from(list).subscribeOn(Schedulers.io()).subscribe(gankResultWelfare -> {
            //避免插入重复数据的逻辑
            long count = MyApplication.getDaoSession().getGankResultWelfareDao().queryBuilder().where(GankResultWelfareDao.Properties._id.eq(gankResultWelfare.get_id())).count();
            if (count == 0) MyApplication.getDaoSession().getGankResultWelfareDao().insertOrReplaceInTx(gankResultWelfare);
        });
    }


    public void addScrollListener() {
        mRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (mGridLayoutManager.getItemCount() == 1) return;//只有一个条目直接返回
                    mLastVisibleItem = mGridLayoutManager.findLastVisibleItemPosition();//获取最后一个可见的条目的角标
                    //当上拉到最后一条时，请求下一页数据
                    if (mLastVisibleItem + 1 == mGridLayoutManager.getItemCount()) {
                        mView.loadMore(true);
                        isLoadMore = true;
                        new Handler().postDelayed(() -> requestData(), 1000);
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
