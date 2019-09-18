package com.rockyw.projectcore.common.bean;

/**
 * @author: WangXueMing
 * @version: 1.0.0
 * @date: 2019/1/17
 */
public class DetailUserBean {

    /**
     * authStatus认证状态(所有都认证通本字段才为认证成功)
     * [{"code":"1","value":"未认证"},{"code":"2","value":"认证中"},{"code":"3","value":"已认证"},{"code":"4","value":"认证失败"}]
     */
    public static final int AUTH_UNDO = 1;
    public static final int AUTH_ING = 2;
    public static final int AUTH_SUCC = 3;
    public static final int AUTH_FAILED = 4;
    /**
     * bankAuthStatus银行卡认证状态
     * [{"code":"1","value":"未认证"},{"code":"2","value":"认证中"},{"code":"3","value":"已认证"},{"code":"4","value":"认证失败"}]
     */
    public static final int BANK_AUTH_UNDO = 1;
    public static final int BANK_AUTH_AUTHING = 2;
    public static final int BANK_AUTH_SUCCESS = 3;
    public static final int BANK_AUTH_FAILED = 4;
    /**
     * emergencyStatus紧急联系人设置情况
     * {"code":"2","value":"已认证"},
     */
    public static final int EMERGENCY_CONTACT_UNAUTH = 1;
    public static final int EMERGENCY_CONTACT_AUTHED = 2;
    /**
     * mailAuthStatus邮箱认证状态
     * [{"code":"1","value":"未认证"},{"code":"2","value":"认证中"},{"code":"3","value":"已认证"},{"code":"4","value":"认证失败"}]
     */
    public static final int EMAIL_AUTH_UNDO = 1;
    public static final int EMAIL_AUTH_AUTHING = 2;
    public static final int EMAIL_AUTH_SUCCESS = 3;
    public static final int EMAIL_AUTH_FAILED = 4;
    /**
     * personAuthStatus身份认证状态
     * [{"code":"1","value":"未认证"},{"code":"2","value":"认证中"},{"code":"3","value":"已认证"},{"code":"4","value":"认证失败"}]
     */
    public static final int PERSON_AUTH_UNDO = 1;
    public static final int PERSON_AUTH_AUTHING = 2;
    public static final int PERSON_AUTH_SUCCESS = 3;
    public static final int PERSON_AUTH_FAILED = 4;
    /**
     * phoneAuthStatus手机号认证状态
     * [{"code":"1","value":"未认证"},{"code":"2","value":"认证中"},{"code":"3","value":"已认证"},{"code":"4","value":"认证失败"}]
     */
    public static final int PHONE_NUM_AUTH_UNDO = 1;
    public static final int PHONE_NUM_AUTH_AUTHING = 2;
    public static final int PHONE_NUM_AUTH_SUCCESS = 3;
    public static final int PHONE_NUM_AUTH_FAILED = 4;
    /**
     * riskAuthStatus风险投资认证状态
     * [{"code":"3","value":"已评估"},{"code":"1","value":"未评估"}]
     */
    public static final int RISK_EVALUATED = 3;
    public static final int RISK_UNEVAL = 1;
    /**
     * tradePasswordStatus交易密码设置状态
     * [{"code":"3","value":"已设置"},{"code":"1","value":"未设置"}]
     */
    public static final int TRADE_PWD_SETED = 3;
    public static final int TRADE_PWD_UN_SET = 1;
    /**
     * 微信认证状态
     * [{"code":"1","value":"未认证"},{"code":"2","value":"认证中"},{"code":"3","value":"已认证"},{"code":"4","value":"认证失败"}]
     */
    public static final int WX_AUTH_UNDO = 1;
    public static final int WX_AUTH_AUTHING = 2;
    public static final int WX_AUTH_SUCCESS = 3;
    public static final int WX_AUTH_FAILED = 4;

    /**
     * key
     */
    public static final String NAME = "name";
    public static final String IDENTITY_ID = "identity_id";
    public static final String ACCOUNT_TYPE = "accountType";
    public static final String AUTH_LEVEL = "authLevel";
    public static final String AUTH_MESSAGE = "authMessage";
    public static final String AUTH_STATUS = "authStatus";
    public static final String BANK_AUTH_STATUS = "bankAuthStatus";
    public static final String CLIENT_SN = "clientSn";
    public static final String EMAIL = "email";
    public static final String EMERGENCY_STATUS = "emergencyStatus";
    public static final String MAIL_AUTH_STATUS = "mailAuthStatus";
    public static final String MOBILE_PHONE = "mobilePhone";
    public static final String PERSON_AUTH_STATUS = "personAuthStatus";
    public static final String PERSON_CARD_TYPE = "personCardType";
    public static final String PHONE_AUTH_STATUS = "phoneAuthStatus";
    public static final String RISK_AUTH_STATUS = "riskAuthStatus";
    public static final String TRADE_PWD_STATUS = "tradePasswordStatus";
    public static final String WX_AUTH_STATUS = "weixinAuthStatus";
    public static final String QUALIFY_INVESTOR_STATUS = "qualifyInvestorStatus";
    public static final String ASSET_PROOF_STATUS = "assetProofStatus";
    /**
     * 新增项，不在接口返回里头，用作保存电话号码
     */
    public static final String MOBILE_PHONE_QUICK_LOGIN = "mobilePhoneQuick";


    public String personName;
    public String personCardNo;

    public String accountType;
    public int authLevel;
    public String authMessage;
    public int authStatus;
    public int bankAuthStatus;
    public int clientSn;
    public String email;
    public int emergencyStatus;
    public int mailAuthStatus;
    public String mobilePhone;
    public int personAuthStatus;
    public int personCardType;
    public int phoneAuthStatus;
    public int riskAuthStatus;
    public int tradePasswordStatus;
    public int weixinAuthStatus;
    /**
     * 新增项，不在接口返回里头，用作保存电话号码
     */
    public String mobilePhoneQuickLogin;
}