package com.dingmouren.easymvp.ui.gallery;

import android.os.Bundle;
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
import com.dingmouren.easymvp.view.OWLoadingView;
import com.dingmouren.easymvp.view.SpeedRecyclerView;

/**
 * Created by dingmouren on 2016/12/3.
 */

public class GalleryFragment extends BaseFragment implements GalleryContract.View{

    Toolbar mToolbar;
    SpeedRecyclerView mRecycler;
    ImageView mBlurImg;
    OWLoadingView mLoading;
    public LinearLayoutManager mLinearLayoutManager;
    public GalleryAdapter mGalleryAdapter;
    public GalleryPresenter mPresenter;
    private CardScaleHelper mCardScaleHepler = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gallery,container,false);
        mToolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        mRecycler = (SpeedRecyclerView) rootView.findViewById(R.id.speed_recycler);
        mBlurImg = (ImageView) rootView.findViewById(R.id.img_blur);
        mLoading = (OWLoadingView) rootView.findViewById(R.id.loading);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //初始化相册布局
        initView();
        //初始化相册数据
        initData();
    }



    private void initView() {
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

    private void initData() {
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
            mLoading.startAnim();
        }else {
            mLoading.stopAnim();
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
    public ImageView getBlurImageView() {
        return mBlurImg;
    }

    @Override
    public CardScaleHelper getCardScaleHelper() {
        return mCardScaleHepler;
    }

}
