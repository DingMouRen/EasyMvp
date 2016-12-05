package com.dingmouren.easymvp.ui.gallery;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.dingmouren.easymvp.base.BasePresenter;
import com.dingmouren.easymvp.base.BaseView;
import com.dingmouren.easymvp.helper.CardScaleHelper;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

/**
 * Created by dingmouren on 2016/12/3.
 */

public interface GalleryContract {

    interface View extends BaseView{
        void setDataRefresh(boolean refresh);
        RecyclerView getRecyclerView();
        GalleryAdapter getGalleryAdapter();
        LinearLayoutManager getLayoutManager();
        ImageView getBlurImageView();
        CardScaleHelper getCardScaleHelper();
    }

    abstract class Presenter<V extends View> extends BasePresenter{

    }
}
