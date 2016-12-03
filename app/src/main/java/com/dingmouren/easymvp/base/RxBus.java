package com.dingmouren.easymvp.base;

import com.jiongbull.jlog.JLog;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

/**
 * Created by dingzi on 2016/11/30.
 */

public class RxBus {
    private static RxBus instance;

    public static synchronized RxBus $(){
        if ( instance == null){
            instance = new RxBus();
        }
        return instance;
    }

    private RxBus(){}

    private ConcurrentHashMap<Object,List<Subject>> subjectMapper = new ConcurrentHashMap<>();

    /**
     * 订阅事件源
     * @param mObservable
     * @param mAction1
     * @return
     */
    public RxBus onEvent(Observable<?> mObservable, Action1<Object> mAction1){
        mObservable.observeOn(AndroidSchedulers.mainThread()).subscribe(mAction1,(e) -> e.printStackTrace());
        return $();
    }

    /**
     * 注册事件源
     * @param tag
     * @param <T>
     * @return
     */
    public <T> Observable<T> register(Object tag){
        List<Subject> subjectList = subjectMapper.get(tag);
        if (subjectList ==  null){
            subjectList = new ArrayList<Subject>();
            subjectMapper.put(tag,subjectList);
        }
        Subject<T,T> subject;
        subjectList.add(subject = PublishSubject.create());
        JLog.d("register",tag + " size:" + subjectList.size());
        return subject;
    }

    public void unregister(Object tag){
        List<Subject> subjects = subjectMapper.get(tag);
        if (subjects != null){
            subjectMapper.remove(tag);
        }
    }

    public RxBus unregister(Object tag,Observable<?> observable){
        if (observable == null){
            return $();
        }

        List<Subject> subjects = subjectMapper.get(tag);
        if (subjects != null){
            subjects.remove((Subject<?,?>) observable);
            if (isEmpty(subjects)){
                subjectMapper.remove(tag);
                JLog.d("unregister",tag + " size:" + subjects.size());
            }
        }
        return  $();
    }

    public void post(Object content){
        post(content.getClass().getName(),content);
    }

    /**
     * 触发事件
     * @param tag
     * @param content
     */
    public void post(Object tag,Object content){
        JLog.d("post","eventName:" + tag);
        List<Subject> subjectList = subjectMapper.get(tag);
        if(!isEmpty(subjectList)){
            for (Subject subject : subjectList){
                subject.onNext(content);
                JLog.d("onEvent","eventName:"+tag);
            }
        }
    }

    public static boolean isEmpty(Collection<Subject> collection){
        return collection == null || collection.isEmpty();
    }

}
