package com.dingmouren.easymvp.ui.gallery;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dingmouren.easymvp.R;
import com.dingmouren.easymvp.base.BaseFragment;
import com.dingmouren.easymvp.helper.CardScaleHelper;
import com.dingmouren.easymvp.view.SpeedRecyclerView;

import butterknife.BindView;

/**
 * Created by dingmouren on 2016/12/3.
 */

public class GalleryFragment extends BaseFragment implements GalleryContract.View{
    @BindView(R.id.speed_recycler)  SpeedRecyclerView mRecycler;
    @BindView(R.id.swipe_refresh) SwipeRefreshLayout mSwipeRefresh;
    public LinearLayoutManager mLinearLayoutManager;
    public GalleryAdapter mGalleryAdapter;
    public GalleryPresenter mPresenter;
    private CardScaleHelper mCardScaleHepler = null;


    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_gallery;
    }

    @Override
    protected void setUpView() {
        //初始化相册布局
        mGalleryAdapter = new GalleryAdapter(getActivity());
        mLinearLayoutManager = new LinearLayoutManager(getActivity(),OrientationHelper.HORIZONTAL,false);
        mRecycler.setLayoutManager(mLinearLayoutManager);
        mRecycler.setHasFixedSize(true);
        mRecycler.setAdapter(mGalleryAdapter);
        //recyclerview绑定scale效果
        mCardScaleHepler = new CardScaleHelper();
        mCardScaleHepler.setCurrentItemPos(2);
        mCardScaleHepler.attachToRecyclerView(mRecycler);
    }

    @Override
    protected void setUpData() {
        //初始化相册数据
        mPresenter = createPresenter();
        mPresenter.requestData();
        mPresenter.addScrollistener();
    }



    /**
     * 创建GalleryPresenter实例
     * @return
     */
    private GalleryPresenter createPresenter(){
        return new GalleryPresenter( (GalleryContract.View)this);
    }

    @Override
    public void setDataRefresh(boolean refresh) {
        if (refresh){
            mSwipeRefresh.setRefreshing(true);
        }else {
            mSwipeRefresh.setRefreshing(false);
        }
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mRecycler;
    }

    @Override
    public GalleryAdapter getGalleryAdapter() {
        return mGalleryAdapter == null ? new GalleryAdapter(getActivity()) : mGalleryAdapter;
    }

    @Override
    public LinearLayoutManager getLayoutManager() {
        return mLinearLayoutManager ==  null ? new LinearLayoutManager(getActivity(), OrientationHelper.HORIZONTAL,false) : mLinearLayoutManager ;
    }

    @Override
    public CardScaleHelper getCardScaleHelper() {
        return mCardScaleHepler;
    }

}
