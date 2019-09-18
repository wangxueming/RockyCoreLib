package com.rockyw.projectcore.common.bean;

import com.rockyw.projectcore.net.BaseResponse;

/**
 * @author: WangXueMing
 * @version: 1.0.0
 * @date: 2019/6/25
 */
public class CompanyInfoResponse extends BaseResponse {

    /**
     * 待提交审核
     */
    public static final int STAT_UNFILE_AUDIT = 1;
    /**
     * 审批中
     */
    public static final int STAT_AUDITING = 2;
    /**
     * 审批通过
     */
    public static final int STAT_AUDIT_SUCCESS = 3;
    /**
     * 审批未通过
     */
    public static final int STAT_AUDIT_FAILED = 4;
    /**
     * 您不满足企业注册条件
     */
    public static final int STAT_NOT_COMPANY_QUALIFY = 5;
    /**
     * 再次审批中
     */
    public static final int STAT_AUDIT_AGAIN = 6;

    public DataBean data;

    public static class DataBean {
        public int approveStatus;
        public String authorizationCardImg;
        public String authorizationEndDate;
        public String authorizationLetterImg;
        public String authorizationStartDate;
        public String authorizerEmail;
        public String authorizerImg;
        public String authorizerName;
        public String bankCode;
        public String businessAccount;
        public String businessLicenseCodeImg;
        public String cityCode;
        public int clientSn;
        public String creditCodeImg;
        public String depositBank;
        public String enterpriseAddress;
        public String enterpriseName;
        public String enterpriseProperty;
        public String enterpriseTel;
        public String legalPersonImg;
        public String papersCode;
        public int papersType;
        public String provinceCode;
        public String registeredAddress;
        public double registeredCapital;
        public String remark;
        public String updateTime;
        public String urgentContactPerson;
        public String urgentContactPhone;
    }
}
