package com.dingmouren.easymvp.bean;

import com.dingmouren.easymvp.base.BaseEntity;

import java.util.List;

/**
 * Created by dingmouren on 2016/12/8.
 */

public class GankWelfare extends BaseEntity {

    /**
     * error : false
     * results : [{"_id":"5848c92e421aa963efd90da4","createdAt":"2016-12-08T10:45:02.271Z","desc":"12-8","publishedAt":"2016-12-08T11:42:08.186Z","source":"chrome","type":"福利","url":"http://ww1.sinaimg.cn/large/610dc034jw1faj6sozkluj20u00nt75p.jpg","used":true,"who":"代码家"},{"_id":"58476013421aa963ed5064da","createdAt":"2016-12-07T09:04:19.739Z","desc":"12-7","publishedAt":"2016-12-07T11:43:57.460Z","source":"chrome","type":"福利","url":"http://ww1.sinaimg.cn/large/610dc034jw1fahy9m7xw0j20u00u042l.jpg","used":true,"who":"daimajia"},{"_id":"58460694421aa939b58d31e3","createdAt":"2016-12-06T08:30:12.824Z","desc":"12-6","publishedAt":"2016-12-06T11:33:36.433Z","source":"chrome","type":"福利","url":"http://ww4.sinaimg.cn/large/610dc034jw1fagrnmiqm1j20u011hanr.jpg","used":true,"who":"daimajia "},{"_id":"5844b8dd421aa939befafb03","createdAt":"2016-12-05T08:46:21.259Z","desc":"12-5","publishedAt":"2016-12-05T11:40:51.351Z","source":"chrome","type":"福利","url":"http://ww4.sinaimg.cn/large/610dc034gw1fafmi73pomj20u00u0abr.jpg","used":true,"who":"daimajia"},{"_id":"5840bd8a421aa939bb4637e9","createdAt":"2016-12-02T08:17:14.322Z","desc":"12-2","publishedAt":"2016-12-02T12:13:34.224Z","source":"chrome","type":"福利","url":"http://ww4.sinaimg.cn/large/610dc034gw1fac4t2zhwsj20sg0izahf.jpg","used":true,"who":"代码家"},{"_id":"583f6a3b421aa939bb4637e1","createdAt":"2016-12-01T08:09:31.936Z","desc":"12-1","publishedAt":"2016-12-01T11:36:13.685Z","source":"chrome","type":"福利","url":"http://ww1.sinaimg.cn/large/610dc034jw1faaywuvd20j20u011gdli.jpg","used":true,"who":"daimajia"},{"_id":"583d96f6421aa939befafacf","createdAt":"2016-11-29T22:55:50.767Z","desc":"11-30","publishedAt":"2016-11-30T11:35:55.916Z","source":"chrome","type":"福利","url":"http://ww1.sinaimg.cn/large/610dc034gw1fa9dca082pj20u00u0wjc.jpg","used":true,"who":"daimajia"},{"_id":"583cc2bf421aa971108b6599","createdAt":"2016-11-29T07:50:23.705Z","desc":"11-29","publishedAt":"2016-11-29T11:38:58.378Z","source":"chrome","type":"福利","url":"http://ww2.sinaimg.cn/large/610dc034jw1fa8n634v0vj20u00qx0x4.jpg","used":true,"who":"daimajia"},{"_id":"583b8285421aa9710cf54c3b","createdAt":"2016-11-28T09:04:05.479Z","desc":"11-28","publishedAt":"2016-11-28T11:32:07.534Z","source":"chrome","type":"福利","url":"http://ww3.sinaimg.cn/large/610dc034jw1fa7jol4pgvj20u00u0q51.jpg","used":true,"who":"daimajia"},{"_id":"58378c0f421aa91cade7a57d","createdAt":"2016-11-25T08:55:43.173Z","desc":"11-25","publishedAt":"2016-11-25T11:29:49.832Z","source":"chrome","type":"福利","url":"http://ww2.sinaimg.cn/large/610dc034jw1fa42ktmjh4j20u011hn8g.jpg","used":true,"who":"dmj"}]
     */

    private boolean error;
    private List<ResultsBean> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
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
}
