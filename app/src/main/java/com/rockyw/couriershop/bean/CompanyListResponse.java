package com.rockyw.couriershop.bean;

import java.util.List;

/**
 * @author: WangXueMing
 * @version: 1.0.0
 * @date: 2019/9/23
 */
public class CompanyListResponse {

    public String request_id;
    public int code;
    public Object msg;
    public DataBean data;

    public static class DataBean {
        public List<String> fields;
        public List<List<String>> items;
    }
}
