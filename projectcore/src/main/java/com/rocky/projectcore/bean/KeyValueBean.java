package com.rocky.projectcore.bean;

/**
 * @author: WangXueMing
 * @version: 1.0.0
 * @date: 2019/1/18
 */
public class KeyValueBean {
    public String key;
    public String value;
    public boolean highLight = false;

    public KeyValueBean() {
    }

    public KeyValueBean(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public KeyValueBean(String key, String value, boolean highLight) {
        this.key = key;
        this.value = value;
        this.highLight = highLight;
    }
}
