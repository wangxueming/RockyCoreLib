package com.rockyw.projectcore.common.bean;

/**
 * @author: WangXueMing
 * @version: 1.0.0
 * @date: 2019/1/7
 */
public class LoginUserBean {
    public static final String ACCESS_TOKEN = "accessToken";
    public static final String MOBILE_PHONE = "mobilePhone";
    public static final String TIME_OUT = "timeout";
    /**
     * 新增项，不在接口返回里头，用作保存电话号码
     */
    public static final String MOBILE_PHONE_QUICK_LOGIN = "mobilePhoneQuick";
    /**
     * 认证accessToken	NOSET	String	认证accessToken
     */
    public String accessToken;
    /**
     * 手机号	NOSET	String	手机号
     */
    public String mobilePhone;
    /**
     * 失效时间	NOSET	String	失效时间
     */
    public String timeout;

    /**
     * ----------以下为额外增项，不属于网络请求的东西
     */
    /**
     * 用户名
     */
    public String userName;
    /**
     * 新增项，不在接口返回里头，用作保存电话号码
     */
    public String mobilePhoneQuickLogin;
}
