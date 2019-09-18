package com.rockyw.projectcore.common.bean;

import com.rockyw.projectcore.net.BaseResponse;

/**
 * @author: WangXueMing
 * @version: 1.0.0
 * @date: 2019/2/1
 */
public class TransUserInfoResponse extends BaseResponse{

    public DataBean data;

    public static class DataBean {
        public int clientSn;
        public String createTime;
        public String password;
        public int status;
        public String updateTime;
        public int userType;
    }
}
