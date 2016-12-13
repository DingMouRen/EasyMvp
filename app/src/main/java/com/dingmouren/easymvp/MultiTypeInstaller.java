package com.dingmouren.easymvp;

import com.dingmouren.easymvp.bean.multitype.GankDataImage;
import com.dingmouren.easymvp.bean.multitype.GankDataText;
import com.dingmouren.easymvp.bean.provider.GankDataImageViewProvider;
import com.dingmouren.easymvp.bean.provider.GankDataTextViewProvider;

import me.drakeet.multitype.GlobalMultiTypePool;

/**
 * Created by dingmouren on 2016/12/13.
 */

public class MultiTypeInstaller {
    static void install(){
        GlobalMultiTypePool.register(GankDataText.class,new GankDataTextViewProvider());
        GlobalMultiTypePool.register(GankDataImage.class,new GankDataImageViewProvider());
    }
}
