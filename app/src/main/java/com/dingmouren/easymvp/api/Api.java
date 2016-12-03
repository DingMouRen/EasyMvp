package com.dingmouren.easymvp.api;

import com.dingmouren.easymvp.bean.GirlPic;
import com.dingmouren.easymvp.bean.GirlResult;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by dingmouren on 2016/11/30.
 */

public interface Api {

    @GET("data/福利/10/{page}")
    Observable<GirlResult> getGirlPics(@Path("page") int page);
}
