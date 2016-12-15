package com.dingmouren.easymvp.ui.picture;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dingmouren.easymvp.base.BasePresenter;
import com.dingmouren.easymvp.base.BaseView;
import com.dingmouren.easymvp.ui.picture.WelfareAdapter;

/**
 * Created by dingmouren on 2016/12/1.
 */

public interface WelfareContract {

    interface View extends BaseView{
        void setDataRefresh(boolean refresh);
        GridLayoutManager getLayoutManager();
        RecyclerView getRecyclerView();
        WelfareAdapter getHomeAdapter();
        boolean getIsNullDatabase();
    }

    abstract class Presenter<V extends BaseView> extends BasePresenter<View>{

    }

}
