package com.dingmouren.easymvp.ui.videos;

import com.dingmouren.easymvp.Constant;
import com.dingmouren.easymvp.api.ApiManager;
import com.dingmouren.easymvp.bean.video.VideoBean;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by dingmouren on 2017/1/11.
 */

public class VideoPresenter implements VideoContract.Presenter {
    private VideoContract.View mView;
    private List<VideoBean> mList;
    private int page = 1;
    public VideoPresenter(VideoContract.View view){
        this.mView = view;
        this.mList = mView.getVideoAdapterList();
    }
    @Override
    public void requestData() {
        ApiManager.getApiInstance().getVideoApiService().getVideoList(Constant.SHOW_API_APP_ID,Constant.SHOW_API_SIGN,"41",String.valueOf(page))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(showapiResBodyBeanVideoResult -> displayData(showapiResBodyBeanVideoResult.showapi_res_body.pagebean.contentlist),this::loadError);
    }

    private void loadError(Throwable throwable){
        throwable.printStackTrace();
//        SnackbarUtils.showSimpleSnackbar(mRecycler,"网络不见了~~");
    }

    private void displayData(List<VideoBean> list){
        for (int i = 0; i < list.size(); i++) {
            mList.add(list.get(i));
        }
        mView.setData();
    }
}
