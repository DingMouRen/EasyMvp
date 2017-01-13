package com.dingmouren.easymvp.bean.video;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by dingmouren on 2017/1/13.
 * 存储封面图的字节数组
 */
@Entity
public class VideoCoverBean {
    private String url;
    private byte[] bytes;
    @Generated(hash = 241037888)
    public VideoCoverBean(String url, byte[] bytes) {
        this.url = url;
        this.bytes = bytes;
    }

    @Generated(hash = 165965975)
    public VideoCoverBean() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}
