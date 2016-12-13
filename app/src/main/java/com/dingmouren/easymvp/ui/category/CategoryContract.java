package com.dingmouren.easymvp.ui.category;

import com.dingmouren.easymvp.base.BasePresenter;
import com.dingmouren.easymvp.base.BaseView;
import com.dingmouren.easymvp.bean.GankContent;

import java.util.List;

/**
 * Created by dingmouren on 2016/12/13.
 */

public interface CategoryContract {

    interface View extends BaseView{
        void setData(List<GankContent> list);
    }

    abstract class Presenter<V extends View> extends BasePresenter{

    }
}
