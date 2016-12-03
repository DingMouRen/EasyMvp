package com.dingmouren.easymvp.bean;

import com.dingmouren.easymvp.base.BaseEntity;

import java.util.List;

/**
 * Created by dingmouren on 2016/11/30.
 */

public class GirlPic extends BaseEntity {


    /**
     * _id : 583d96f6421aa939befafacf
     * createdAt : 2016-11-29T22:55:50.767Z
     * desc : 11-30
     * publishedAt : 2016-11-30T11:35:55.916Z
     * source : chrome
     * type : 福利
     * url : http://ww1.sinaimg.cn/large/610dc034gw1fa9dca082pj20u00u0wjc.jpg
     * used : true
     * who : daimajia
     */

    private String _id;
    private String createdAt;
    private String desc;
    private String publishedAt;
    private String source;
    private String type;
    private String url;
    private boolean used;
    private String who;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }
}
