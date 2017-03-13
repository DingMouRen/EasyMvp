package com.dingmouren.easymvp.bean.video;

import java.util.List;

/**
 * Created by dingmouren on 2017/1/11.
 */

public class VideoResult<T> {


    private int showapi_res_code;
    private String showapi_res_error;
    public T showapi_res_body;

    public static class ShowapiResBodyBean<T> {


        private int ret_code;
        public T pagebean;

        public static class PagebeanBean<T> {


            private int allPages;
            private int currentPage;
            private int allNum;
            private int maxResult;
            public T contentlist;
        }
    }
}
