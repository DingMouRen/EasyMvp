package com.dingmouren.easymvp.ui.home;

import android.webkit.WebView;

import com.dingmouren.easymvp.base.BasePresenter;
import com.dingmouren.easymvp.base.BaseView;
import com.dingmouren.easymvp.bean.GankContent;

import java.util.List;

/**
 * Created by dingmouren on 2016/12/8.
 */

public interface HomeContract {

    interface View extends BaseView{
        void setDataRefresh(boolean refresh);
        void setData(List<GankContent> list,String girlImgUrl);
    }

    abstract class Presenter<V extends BaseView> extends BasePresenter<View> {

    }
}
