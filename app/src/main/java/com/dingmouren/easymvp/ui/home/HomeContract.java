package com.dingmouren.easymvp.ui.home;

import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dingmouren.easymvp.base.BasePresenter;
import com.dingmouren.easymvp.base.BaseView;
import com.dingmouren.easymvp.bean.GankContent;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by dingmouren on 2016/12/8.
 */

public interface HomeContract {

    interface View extends BaseView{
        void setDataRefresh(boolean refresh);
        void setData();
        RelativeLayout getProgressBarRelative();
        TextView getProgressBarTextView();
        ProgressBar getProgressbar();
        List<Object> getItems();
    }

    interface Presenter<V extends BaseView> extends BasePresenter<View> {
        void requestData();
        void loadError(Throwable throwable);
    }
}
