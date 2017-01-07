package com.dingmouren.easymvp.ui.category;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dingmouren.easymvp.base.BasePresenter;
import com.dingmouren.easymvp.base.BaseView;
import com.dingmouren.easymvp.bean.GankContent;

import java.util.List;

/**
 * Created by dingmouren on 2016/12/14.
 */

public interface CategoryContract {
    interface View extends BaseView{
        void setRefresh(boolean refresh);
        RecyclerView getRecyclerView();
        LinearLayoutManager getLayoutManager();
        String getType();
        void setData();
        List<Object> getItems();
        void loadMore(boolean loadMore);
        boolean isRefreshing();
    }
    interface   Presenter<V extends View > extends BasePresenter{

    }
}
