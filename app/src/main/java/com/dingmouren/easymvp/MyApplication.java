package com.dingmouren.easymvp;

import android.app.Application;

import com.jiongbull.jlog.JLog;

/**
 * Created by dingmouren on 2016/12/1.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        JLog.init(this)
                .setDebug(BuildConfig.DEBUG);
        MultiTypeInstaller.install();
    }
}
