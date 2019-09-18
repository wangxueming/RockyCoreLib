package com.rockyw.projectcore.common.bean;

/**
 * @author: WangXueMing
 * @version: 1.0.0
 * @date: 2019/2/11
 */
public class CertCodeBean {
    public String code;
    public String value;
    public String img;

    public CertCodeBean() {
    }

    public CertCodeBean(String code, String value, String img) {
        this.code = code;
        this.value = value;
        this.img = img;
    }
}
