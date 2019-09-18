package com.rockyw.projectcore.bean;

/**
 * @author: WangXueMing
 * @version: 1.0.0
 * @date: 2019/1/18
 */
public class KeyValueBean {
    public int index;
    public String key;
    public String value;
    public boolean highLight = false;
    public String showMoreMsg;

    public KeyValueBean() {
    }

    public KeyValueBean(int index, String key, String value) {
        this.index = index;
        this.key = key;
        this.value = value;
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

    public KeyValueBean(String key, String value, boolean highLight, String showMoreMsg) {
        this.key = key;
        this.value = value;
        this.highLight = highLight;
        this.showMoreMsg = showMoreMsg;
    }

    public KeyValueBean(int index, String key, String value, boolean highLight, String showMoreMsg) {
        this.index = index;
        this.key = key;
        this.value = value;
        this.highLight = highLight;
        this.showMoreMsg = showMoreMsg;
    }
}
