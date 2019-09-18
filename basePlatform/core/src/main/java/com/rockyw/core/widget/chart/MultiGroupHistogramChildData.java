package com.rockyw.core.widget.chart;

/**
 * 柱状图的子item
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/4/17
 */
public class MultiGroupHistogramChildData {
    private float value;
    private String suffix;

    public float getValue() {
        return value;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
