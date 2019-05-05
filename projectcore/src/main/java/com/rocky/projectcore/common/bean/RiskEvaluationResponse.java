package com.rocky.projectcore.common.bean;

import com.rocky.projectcore.net.BaseResponse;

/**
 * @author: WangXueMing
 * @version: 1.0.0
 * @date: 2019/3/18
 */
public class RiskEvaluationResponse extends BaseResponse {

    public static final String RISK_EVAL_TYPE = "riskEval2Type";
    public static final String RISK_EVAL_ANSWERS = "riskEval2Answers";
    public static final String RISK_EVAL_SCORE = "riskEval2Score";
    public static final String RISK_EVAL_STATUS = "riskEval2Status";

    /**
     * 风险评估结果1.保守型 2.谨慎型 3.稳健型 4.进取型 5.激进型
     */
    public static final int RISK_EVAL_RES_1 = 1;
    public static final int RISK_EVAL_RES_2 = 2;
    public static final int RISK_EVAL_RES_3 = 3;
    public static final int RISK_EVAL_RES_4 = 4;

    /**
     * 风险评估状态值1.未评估 2.已过期 3.未通过 4.已通过
     */
    public static final int RISK_EVAL_STATUS_UNAUDIT = 1;
    public static final int RISK_EVAL_STATUS_EXPIRED = 2;
    public static final int RISK_EVAL_STATUS_AUDIT_FAILED = 3;
    public static final int RISK_EVAL_STATUS_AUDIT_SUCCESS = 4;

    public DataBean data;

    public static class DataBean {
        public int clientSn;
        public String createdTime;
        public int type;
        public String riskAnswer;
        public String updateTime;
        public int score;
        public int status;
    }
}
