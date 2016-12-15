package com.dingmouren.easymvp;

import android.app.Application;

import com.dingzi.greendao.DaoMaster;
import com.dingzi.greendao.DaoSession;
import com.jiongbull.jlog.JLog;

import org.greenrobot.greendao.database.Database;

/**
 * Created by dingmouren on 2016/12/1.
 */

public class MyApplication extends Application {
    public static DaoSession mDaoSession;
    @Override
    public void onCreate() {
        super.onCreate();
        JLog.init(this)
                .setDebug(BuildConfig.DEBUG);
        MultiTypeInstaller.install();//复杂列表布局注册
        initGreenDao();//初始化greendao数据库
    }

    private void initGreenDao() {
        //DevOpenHelper每次数据库升级会清空数据，一般用于开发
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,"easyMvp-db",null);
        Database db = helper.getWritableDb();
        mDaoSession = new DaoMaster(db).newSession();
    }

    public static DaoSession getDaoSession(){
        return mDaoSession;
    }
}
