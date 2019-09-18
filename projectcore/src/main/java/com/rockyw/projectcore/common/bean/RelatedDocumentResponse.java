package com.rockyw.projectcore.common.bean;

import com.rockyw.projectcore.net.BaseResponse;

/**
 * @author: WangXueMing
 * @version: 1.0.0
 * @date: 2019/4/26
 */
public class RelatedDocumentResponse extends BaseResponse{
    public DataBean data;

    public static class DataBean {
        public String productManualUrl;
        public String productSubscriptionAgreementUrl;
        public String productLegalOpinionUrl;
        public String productAuditReportUrl;
        public String productRiskRevelationUrl;
        public String productLetterOfUndertakingUrl;
    }
}
