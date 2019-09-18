package com.rockyw.projectcore.module.operate.bean;

import com.rockyw.projectcore.net.BaseResponse;

import java.util.List;

/**
 * @author: WangXueMing
 * @version: 1.0.0
 * @date: 2019/7/2
 */
public class RedEnvelopeNumResponse extends BaseResponse {

    public boolean success;
    public List<DataBean> data;

    public static class DataBean {
        public int num;
        public int status;
    }
}
