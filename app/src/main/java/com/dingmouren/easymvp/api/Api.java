package com.dingmouren.easymvp.api;

import com.dingmouren.easymvp.bean.GankResultCategory;
import com.dingmouren.easymvp.bean.GankResult;
import com.dingmouren.easymvp.bean.GankResultWelfare;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by dingmouren on 2016/11/30.
 */

public interface Api {

    //获取最新一天的数据
    @GET("history/content/1/1")
    Observable<GankResult<List<GankResultCategory>>> getDataUpToDate();

    //获取发布过的日期
    @GET("day/history")
    Observable<GankResult<List<String>>> getGankDatePushed();

    //获取具体某天的干货内容
    @GET("day/{date}")
    Observable<GankResult<GankResultCategory>>  getGankDay(@Path("date") String date);

    @GET("data/福利/1/1")
    Observable<GankResult<List<GankResultWelfare>>> getFirstGirl();

    @GET("data/福利/10/{page}")
    Observable<GankResult<List<GankResultWelfare>>> getGirlPics(@Path("page") int page);

}
