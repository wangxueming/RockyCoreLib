package com.rocky.core.widget.chart;

import java.util.List;

/**
 * 柱形图的group数据
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/4/17
 */
public class MultiGroupHistogramGroupData {

    private String groupName;
    private List<MultiGroupHistogramChildData> childDataList;

    public String getGroupName() {
        return groupName;
    }

    public List<MultiGroupHistogramChildData> getChildDataList() {
        return childDataList;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setChildDataList(List<MultiGroupHistogramChildData> childDataList) {
        this.childDataList = childDataList;
    }
}
