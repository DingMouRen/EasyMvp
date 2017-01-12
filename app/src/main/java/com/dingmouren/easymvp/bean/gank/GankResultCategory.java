package com.dingmouren.easymvp.bean.gank;

import com.dingmouren.easymvp.base.BaseEntity;

import java.util.List;

/**
 * Created by dingmouren on 2016/12/8.
 */

public class GankResultCategory extends BaseEntity {


        private List<GankContent> Android;
        private List<GankContent> iOS;
        private List<GankContent> 休息视频;
        private List<GankContent> 前端;
        private List<GankContent> 拓展资源;
        private List<GankResultWelfare> 福利;

    public List<GankContent> getAndroid() {
        return Android;
    }

    public void setAndroid(List<GankContent> android) {
        Android = android;
    }

    public List<GankContent> getiOS() {
        return iOS;
    }

    public void setiOS(List<GankContent> iOS) {
        this.iOS = iOS;
    }

    public List<GankContent> get休息视频() {
        return 休息视频;
    }

    public void set休息视频(List<GankContent> 休息视频) {
        this.休息视频 = 休息视频;
    }

    public List<GankContent> get前端() {
        return 前端;
    }

    public void set前端(List<GankContent> 前端) {
        this.前端 = 前端;
    }

    public List<GankContent> get拓展资源() {
        return 拓展资源;
    }

    public void set拓展资源(List<GankContent> 拓展资源) {
        this.拓展资源 = 拓展资源;
    }

    public List<GankResultWelfare> get福利() {
        return 福利;
    }

    public void set福利(List<GankResultWelfare> 福利) {
        this.福利 = 福利;
    }
}


