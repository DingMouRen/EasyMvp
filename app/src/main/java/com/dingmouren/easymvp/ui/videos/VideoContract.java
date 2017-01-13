package com.dingmouren.easymvp.ui.videos;

import android.graphics.Bitmap;

import com.dingmouren.easymvp.base.BasePresenter;
import com.dingmouren.easymvp.base.BaseView;
import com.dingmouren.easymvp.bean.video.VideoBean;

import java.util.List;

/**
 * Created by dingmouren on 2017/1/11.
 */

public interface VideoContract {

    interface View extends BaseView{
        void setData();
        List<VideoBean> getVideoAdapterList();
    }

    interface Presenter<V extends View> extends BasePresenter{
        void requestData();
    }
}
