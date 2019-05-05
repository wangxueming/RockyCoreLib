package com.rocky.projectcore.net;

import android.text.TextUtils;

import static com.rocky.projectcore.net.BaseObserver.SUCCESS;

/**
 * 基本Json结构
 *
 * @author wangxueming
 * @version 1.0.0
 * @date 2018/12/14
 */
public class BaseResponse {

    private String retCode;
    private String retMsg;

    public String getCode() {
        return retCode;
    }

    public void setCode(String code) {
        this.retCode = code;
    }

    public String getMessage() {
        return retMsg;
    }

    public void setMessage(String message) {
        this.retMsg = message;
    }

    public boolean isSuccess() {
        return TextUtils.equals(getCode(), SUCCESS);
    }
}
