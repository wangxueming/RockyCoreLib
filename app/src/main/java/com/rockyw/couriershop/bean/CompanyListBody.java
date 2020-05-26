package com.rockyw.couriershop.bean;

/**
 * @author: WangXueMing
 * @version: 1.0.0
 * @date: 2019/9/23
 */
public class CompanyListBody {

    public String api_name;
    public String token;
    public ParamsBean params;
    public String fields;

    public static class ParamsBean {
        public String list_status;
    }
}
