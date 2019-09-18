package com.rockyw.core.widget.verticalText;

import java.util.ArrayList;
import java.util.List;

/**
 * VerticalRollingTextView的数据适配器
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/4/17
 */
public abstract class DataSetAdapter<T> {

    private List<T> data = new ArrayList<>();

    public DataSetAdapter() {
    }

    public DataSetAdapter(List<T> data) {
        this.data = data;
    }

    /**
     * @param index 当前角标
     * @return 待显示的字符串
     */
    final public CharSequence getText(int index) {
        return text(data.get(index));
    }

    protected abstract CharSequence text(T t);

    public int getItemCount() {
        return null == data || data.isEmpty() ? 0 : data.size();
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public boolean isEmpty() {
        return null == data || data.isEmpty();
    }
}
