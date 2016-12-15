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

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static android.R.id.list;

/**
 * Created by dingmouren on 2016/12/1.
 */

public class WelfarePresenter extends WelfareContract.Presenter<WelfareContract.View> {

    public static final String TAG = WelfarePresenter.class.getName();

    public WelfareContract.View mHomeView;
    public GridLayoutManager mGridLayoutManager;
    public RecyclerView mRecycler;
    public WelfareAdapter mWelfareAdapter;

    private List<GankResultWelfare> mList = new ArrayList<>();
    private int mPage = 1;
    private int mLastVisibleItem;
    private boolean isLoadMore = false;//是否加载更多
    private GankResultWelfareDao mWelFareDao;
    private boolean isNullDatabase;
    private static final int countDatabase = 10;

    public WelfarePresenter(WelfareContract.View view) {
        this.mHomeView = view;
        mGridLayoutManager = mHomeView.getLayoutManager();
        mRecycler = mHomeView.getRecyclerView();
        mWelfareAdapter = mHomeView.getHomeAdapter();
        isNullDatabase = mHomeView.getIsNullDatabase();
        mWelFareDao = MyApplication.getDaoSession().getGankResultWelfareDao();
    }

    /**
     * 请求数据
     */
    public void requestData() {
        if (isLoadMore && isNullDatabase) {
            mPage = mPage + 1;
            JLog.e(TAG,"加载第"+ mPage+"页");
        } else if (isLoadMore && !isNullDatabase) {
            isNullDatabase = true;//设置成true，目的是让此处只执行一次
            mPage = countDatabase /10 + 1;
            JLog.e(TAG,"加载第"+ mPage+"页");
        }
        JLog.e(TAG,"实际加载第"+ mPage+"页");
        ApiManager.getApiInstance().mApiService.getGirlPics(mPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listGankResult -> displayData(listGankResult.results), this::loadError);
    }

    private void loadError(Throwable throwable) {
        throwable.printStackTrace();
        mPage = mPage - 1;//不管是不是加载了数据库的数据，都从第一页开始加载
        mHomeView.setDataRefresh(false);
        SnackbarUtils.showSimpleSnackbar(mRecycler, "网络不见了~~");
    }

    /**
     * 显示获取的数据
     *
     * @param list
     */
    public void displayData(List<GankResultWelfare> list) {
        if (list == null) {
            mHomeView.setDataRefresh(false);
            return;
        } else {
            mList.addAll(list);
        }
        saveWelFares(mList);//插入数据库
        mWelfareAdapter.setList(mList);
        mWelfareAdapter.notifyDataSetChanged();
        mHomeView.setDataRefresh(false);
    }

    /**
     * 将数据插入数据库
     *
     * @param list
     */
    private void saveWelFares(List<GankResultWelfare> list) {

        for (int i = 0; i < list.size(); i++) {
            mWelFareDao.insertOrReplace(list.get(i));//插入数据库 存在就覆盖
            JLog.e(TAG, "插入---" + list.get(i).getDesc());
        }

    }

    /**
     * 从数据库取20条数据
     */
    public boolean setDataFormDatabase() {
        List<GankResultWelfare> list = mWelFareDao.queryBuilder().limit(countDatabase).list();
        JLog.e(TAG, "数据库取出---" + list.size() + "条数据");
        mWelfareAdapter.setList(list);//从数据库中查询20条
        mWelfareAdapter.notifyDataSetChanged();
        mHomeView.setDataRefresh(false);
        if (0 == list.size()) {
            return false;
        }else {
            return true;
        }
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
                        mHomeView.setDataRefresh(true);
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
