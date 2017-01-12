package com.dingmouren.easymvp.api;


import com.dingmouren.easymvp.bean.video.VideoBean;
import com.dingmouren.easymvp.bean.video.VideoResult;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by dingmouren on 2017/1/11.
 */

public interface VideoApi {
    //获取视频列表
    @GET("255-1")
    Observable<VideoResult<VideoResult.ShowapiResBodyBean<VideoResult.ShowapiResBodyBean.PagebeanBean<List<VideoBean>>>>>
    getVideoList(@Query("showapi_appid") String showapi_appid,@Query("showapi_sign")String showapi_sign,@Query("type") String type, @Query("page") String page);
}
