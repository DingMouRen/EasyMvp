package com.dingmouren.easymvp.bean;

import com.dingmouren.easymvp.base.BaseEntity;

import java.util.List;

/**
 * Created by dingmouren on 2016/11/30.
 */

public class GirlResult extends BaseEntity {
    /**
     * error : false
     * results : [{"_id":"583d96f6421aa939befafacf","createdAt":"2016-11-29T22:55:50.767Z","desc":"11-30","publishedAt":"2016-11-30T11:35:55.916Z","source":"chrome","type":"福利","url":"http://ww1.sinaimg.cn/large/610dc034gw1fa9dca082pj20u00u0wjc.jpg","used":true,"who":"daimajia"},{"_id":"583cc2bf421aa971108b6599","createdAt":"2016-11-29T07:50:23.705Z","desc":"11-29","publishedAt":"2016-11-29T11:38:58.378Z","source":"chrome","type":"福利","url":"http://ww2.sinaimg.cn/large/610dc034jw1fa8n634v0vj20u00qx0x4.jpg","used":true,"who":"daimajia"},{"_id":"583b8285421aa9710cf54c3b","createdAt":"2016-11-28T09:04:05.479Z","desc":"11-28","publishedAt":"2016-11-28T11:32:07.534Z","source":"chrome","type":"福利","url":"http://ww3.sinaimg.cn/large/610dc034jw1fa7jol4pgvj20u00u0q51.jpg","used":true,"who":"daimajia"},{"_id":"58378c0f421aa91cade7a57d","createdAt":"2016-11-25T08:55:43.173Z","desc":"11-25","publishedAt":"2016-11-25T11:29:49.832Z","source":"chrome","type":"福利","url":"http://ww2.sinaimg.cn/large/610dc034jw1fa42ktmjh4j20u011hn8g.jpg","used":true,"who":"dmj"},{"_id":"58362e82421aa9721eb68ac1","createdAt":"2016-11-24T08:04:18.98Z","desc":"11-24","publishedAt":"2016-11-24T11:40:53.615Z","source":"chrome","type":"福利","url":"http://ww3.sinaimg.cn/large/610dc034jw1fa2vh33em9j20u00zmabz.jpg","used":true,"who":"daimajia "},{"_id":"58350b5e421aa972148296ed","createdAt":"2016-11-23T11:22:06.516Z","desc":"11-23","publishedAt":"2016-11-23T11:27:52.847Z","source":"chrome","type":"福利","url":"http://ww1.sinaimg.cn/large/610dc034gw1fa1vkn6nerj20u011gdjm.jpg","used":true,"who":"代码家"},{"_id":"5833b7f1421aa926e26ae819","createdAt":"2016-11-22T11:13:53.663Z","desc":"11-22","publishedAt":"2016-11-22T11:30:13.971Z","source":"chrome","type":"福利","url":"http://ww4.sinaimg.cn/large/610dc034gw1fa0ppsw0a7j20u00u0thp.jpg","used":true,"who":"daimajia"},{"_id":"58326224421aa929ac960b05","createdAt":"2016-11-21T10:55:32.265Z","desc":"11-21","publishedAt":"2016-11-21T11:16:48.599Z","source":"chrome","type":"福利","url":"http://ww2.sinaimg.cn/large/610dc034gw1f9zjk8iaz2j20u011hgsc.jpg","used":true,"who":"代码家"},{"_id":"582e4c38421aa94ffa9f7641","createdAt":"2016-11-18T08:32:56.22Z","desc":"AB","publishedAt":"2016-11-18T11:21:35.425Z","source":"chrome","type":"福利","url":"http://ww2.sinaimg.cn/large/610dc034jw1f9vyl2fqi0j20u011habc.jpg","used":true,"who":"daimajia "},{"_id":"582cf408421aa95002741a8f","createdAt":"2016-11-17T08:04:24.781Z","desc":"11-17","publishedAt":"2016-11-17T11:32:04.807Z","source":"chrome","type":"福利","url":"http://ww2.sinaimg.cn/large/610dc034jw1f9us52puzsj20u00u0jtd.jpg","used":true,"who":"daimajia "}]
     */

    private boolean error;
    private List<GirlPic> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<GirlPic> getResults() {
        return results;
    }

    public void setResults(List<GirlPic> results) {
        this.results = results;
    }
}
