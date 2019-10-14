package com.rockyw.weather.bean;

/**
 * @author: WangXueMing
 * @version: 1.0.0
 * @date: 2019/9/22
 */
public class StockQuotationBody {
    public String api_name;
    public String token;
    public ParamsBean params;
    public String fields;

    public static class ParamsBean {
        public String ts_code;
        public String start_date;
        public String end_date;
    }
}
