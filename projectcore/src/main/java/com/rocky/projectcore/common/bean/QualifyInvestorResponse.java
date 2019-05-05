package com.rocky.projectcore.common.bean;

import com.rocky.projectcore.net.BaseResponse;

/**
 * @author: WangXueMing
 * @version: 1.0.0
 * @date: 2019/3/18
 */
public class QualifyInvestorResponse extends BaseResponse{

    /**
     * 合格投资者类型 1.未确定 2.已确定 3.已认定
     */
    public static final int QUA_INV_UNCONFIRM = 1;
    public static final int QUA_INV_CONFIRMED = 2;
    public static final int QUA_INV_IDENTITY = 3;
    /**
     * 资料上传状态1.未上传 2.待审核 3.审核通过 4.审核不通过
     */
    public static final String ASSET_PROOF_UN_UPLOAD = "1";
    public static final String ASSET_PROOF_WAIT_AUDIT = "2";
    public static final String ASSET_PROOF_AUDIT_SUCCESS = "3";
    public static final String ASSET_PROOF_AUDIT_FAILED = "4";

    public DataBean data;

    public static class DataBean {
        public int clientSn;
        public String createdTime;
        public int type;
        public String status;
        public String updateTime;
        public String picUrls;
        public String remark;
    }
}
