package com.dingmouren.easymvp.ui.category;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dingmouren.easymvp.MyApplication;
import com.dingmouren.easymvp.api.ApiManager;
import com.dingmouren.easymvp.bean.gank.GankContent;
import com.dingmouren.easymvp.util.NetworkUtil;
import com.dingmouren.easymvp.util.SnackbarUtils;
import com.dingzi.greendao.GankContentDao;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by dingmouren on 2016/12/14.
 */

public class CategoryPresenter implements CategoryContract.Presenter{
    private static final String TAG = CategoryPresenter.class.getName();
    private CategoryContract.View mView;
    private RecyclerView mRecycler;
    private LinearLayoutManager mLinearLayoutManager;
    private String mStype;
    private List<GankContent> mList = new ArrayList<>();

    private int pageIndex = 1;
    private int mLastVisibleItem;
    private boolean isLoadMore = false;
    private List<Object> mItems;
    private long count;//用来记录从数据库取出指定项的数量

    public CategoryPresenter(CategoryContract.View view) {
        this.mView = view;
        this.mRecycler = mView.getRecyclerView();
        this.mLinearLayoutManager = mView.getLayoutManager();
        this.mStype = mView.getType();
        this.mItems = mView.getItems();
    }

    public void requestData(){
        if (mView.isRefreshing() && !isLoadMore && NetworkUtil.isAvailable(mRecycler.getContext())){//这里加了一个对网络状态的判断，防止了一个bug的发生，情景是下拉刷新时滑动recyclerview崩溃的异常，
            mItems.clear();//请求第一页时，将之前列表显示数据清空
            pageIndex = 1;
        }else if (!mView.isRefreshing() && isLoadMore){
            pageIndex++;
        }
        ApiManager.getApiInstance().getGankApiService().getCategoryGankContent(mStype,pageIndex)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listGankResult -> parseData(listGankResult.results),this::loadError);

    }

    private void loadError(Throwable throwable){
        throwable.printStackTrace();
        mView.setRefresh(false);
        SnackbarUtils.showSimpleSnackbar(mRecycler,"网络不见了~~");
    }

    /**
     * 将获取的数据添加到items
     * @param list
     */
    private void parseData(List<GankContent> list){
        for (int i = 0; i < list.size(); i++) {
            mItems.add(list.get(i));
        }
        mView.setData();
        //插入不重复的数据到数据库
        Observable.from(list).subscribeOn(Schedulers.io()).subscribe(gankContent -> {
            count = MyApplication.getDaoSession().getGankContentDao().queryBuilder().where(GankContentDao.Properties._id.eq(gankContent.get_id())).count();
            if (0 == count) MyApplication.getDaoSession().getGankContentDao().insertOrReplace(gankContent);
        });
    }

    /**
     * 对Recyclerview的监听，滑动到最底部时，自动加载下一页
     */

    public void addScrollListener(){
        mRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if ( newState == RecyclerView.SCROLL_STATE_IDLE){
                    if (mLinearLayoutManager.getItemCount() == 1) return;//只有一个条目直接返回
                    mLastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();//获取最后一个可见的条目的角标
                    //当上拉到最后一条时，请求下一页数据
                    if (mLastVisibleItem + 1 == mLinearLayoutManager.getItemCount()){
                        mView.loadMore(true);
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
