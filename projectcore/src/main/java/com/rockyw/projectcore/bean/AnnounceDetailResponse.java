package com.rockyw.projectcore.bean;

import com.rockyw.projectcore.net.BaseResponse;

/**
 * @author: WangXueMing
 * @version: 1.0.0
 * @date: 2019/7/19
 */
public class AnnounceDetailResponse extends BaseResponse {

    public DataBean data;

    public static class DataBean {
        public String address;
        public String author;
        public String detail;
        public String digest;
        public String imageUrl;
        public String nextSn;
        public String nextTitle;
        public String preSn;
        public String preTitle;
        public String publishDate;
        public int sn;
        public int subType;
        public String title;
        public String type;
    }
}
