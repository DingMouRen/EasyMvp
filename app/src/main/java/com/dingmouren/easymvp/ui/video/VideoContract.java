package com.dingmouren.easymvp.ui.video;

import android.content.Context;

import com.dingmouren.easymvp.base.BasePresenter;
import com.dingmouren.easymvp.base.BaseView;

/**
 * Created by dingmouren on 2016/12/7.
 */

public interface VideoContract {

    interface View extends BaseView{
        Presenter createPresenter(Context context);
    }

    interface Presenter<V extends BaseView> extends BasePresenter<View>{

    }
}
