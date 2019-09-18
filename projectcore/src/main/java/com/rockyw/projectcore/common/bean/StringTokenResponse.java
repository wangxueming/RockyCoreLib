package com.rockyw.projectcore.common.bean;

import com.rockyw.projectcore.net.BaseResponse;

/**
 * @author: WangXueMing
 * @version: 1.0.0
 * @date: 2019/5/16
 */
public class StringTokenResponse extends BaseResponse{

    public DataBean data;

    public static class DataBean {
        public String isRegister;
        public String stringToken;
        public String tokenType;
    }
}
