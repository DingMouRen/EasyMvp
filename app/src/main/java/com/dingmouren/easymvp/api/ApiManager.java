package com.dingmouren.easymvp.api;

import com.jiongbull.jlog.JLog;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dingmouren on 2016/11/30.
 */

public class ApiManager {

    private static final String GANK_BASE_URL = "http://gank.io/api/";
    private static final int READ_TIME_OUT = 5;
    private static final int CONNECT_TIME_OUT = 5;
    protected static final Object monitor = new Object();

    public Retrofit mRetrofit;
    public Api mApiService  ;

    //构造方法私有化，目的是为了只创建一个实例，并且在这里设置打印log和
    private ApiManager(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> showRetrofitLog(message)).setLevel(HttpLoggingInterceptor.Level.BODY);//打印retrofit日志
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(READ_TIME_OUT,TimeUnit.SECONDS)
                .connectTimeout(CONNECT_TIME_OUT,TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .build();
        mRetrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(GANK_BASE_URL)
                .build();
        mApiService =  mRetrofit.create(Api.class);

    }

    /**
     * 单例对象持有者
     */
    private static class SingletonHolder{
        private static final ApiManager INSTANCE = new ApiManager();
    }

    /**
     * 获取ApiManager单例对象
     * @return
     */
     public static ApiManager getApiInstance(){
         return SingletonHolder.INSTANCE;
     }





    /**
     * 打印日志
     * 返回的是json，就打印格式化好了的json，不是json就原样打印
     * @param message
     */
    private void showRetrofitLog(String message){
        if (message.startsWith("{")){
            JLog.json(message);
        }else {
            JLog.e("Retrofit:",message);
        }
    }

}
