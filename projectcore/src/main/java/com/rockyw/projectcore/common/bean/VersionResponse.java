package com.rockyw.projectcore.common.bean;

        import com.rockyw.projectcore.net.BaseResponse;

/**
 * @author: WangXueMing
 * @version: 1.0.0
 * @date: 2019/3/6
 */
public class VersionResponse extends BaseResponse {

    public DataBean data;

    public static class DataBean {
        public String channel;
        public String content;
        public String createdTime;
        public String downloadUrl;
        public String md5;
        public String packageName;
        public int platform;
        public String remark;
        public int sn;
        public String title;
        public int type;
        public String updateTime;
        public String versionNumber;
    }
}
