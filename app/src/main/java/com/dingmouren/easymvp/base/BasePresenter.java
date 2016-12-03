package com.dingmouren.easymvp.base;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Created by dingmouren on 2016/12/1.
 */

public abstract class BasePresenter<V> {
    public Reference<V> mViewReference;
    public RxManager mRxManager = new RxManager();
    public void attachView(V view){
        mViewReference = new WeakReference<V>(view);
    }
    public V getView(){
        return mViewReference.get();
    }

    public boolean isViewAttached(){
        return mViewReference != null&&mViewReference.get() != null;
    }

    public void detachView(){
        if (mViewReference != null){
            mViewReference.clear();
            mViewReference =  null;
        }
    }
}
