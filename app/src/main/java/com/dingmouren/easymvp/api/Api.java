package com.dingmouren.easymvp.api;

import com.dingmouren.easymvp.bean.GankDataUpToDate;
import com.dingmouren.easymvp.bean.GankWelfare;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by dingmouren on 2016/11/30.
 */

public interface Api {

    //获取最新一天的数据
    @GET("history/content/1/1")
    Observable<GankDataUpToDate> getDataUpToDate();

    @GET("data/福利/10/{page}")
    Observable<GankWelfare> getGirlPics(@Path("page") int page);

}
