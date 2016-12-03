package com.dingmouren.easymvp.ui.home;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.dingmouren.easymvp.base.BasePresenter;
import com.dingmouren.easymvp.base.BaseView;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

/**
 * Created by dingmouren on 2016/12/1.
 */

public interface HomeContract {

    interface View extends BaseView{
        void setDataRefresh(boolean refresh);
        GridLayoutManager getLayoutManager();
        RecyclerView getRecyclerView();
        HomeAdapter getHomeAdapter();
    }

    abstract class Presenter<V extends BaseView> extends BasePresenter<View>{

    }

}
