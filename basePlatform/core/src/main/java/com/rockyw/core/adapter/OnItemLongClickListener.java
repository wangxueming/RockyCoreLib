package com.rockyw.core.adapter;

import android.support.v7.widget.RecyclerView;

/**
 * 适用MultiType的长按监听(单类型继续使用CommonAdapter中的点击监听)
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2018/12/25
 */
public interface OnItemLongClickListener<T, VH extends RecyclerView.ViewHolder> {
    /**
     * 绑定BaseItemViewBinder中的Item longClick事件
     * 这是longClick监听回调
     *
     * @param holder
     * @param item
     * @param position
     */
    boolean onItemLongClick(VH holder, T item, int position);
}
