package com.dingmouren.easymvp.bean;

import com.dingmouren.easymvp.base.BaseEntity;

import java.util.List;

/**
 * Created by dingmouren on 2016/12/8.
 */

public class GankResultWelfare extends BaseEntity {

    /**
     * _id : 5848c92e421aa963efd90da4
     * createdAt : 2016-12-08T10:45:02.271Z
     * desc : 12-8
     * publishedAt : 2016-12-08T11:42:08.186Z
     * source : chrome
     * type : 福利
     * url : http://ww1.sinaimg.cn/large/610dc034jw1faj6sozkluj20u00nt75p.jpg
     * used : true
     * who : 代码家
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
