package com.rocky.core.adapter;

import android.support.v7.widget.RecyclerView;

/**
 * 适用MultiType的点击监听(单类型继续使用CommonAdapter中的点击监听)
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2018/12/25
 */
public interface OnItemClickListener<T, VH extends RecyclerView.ViewHolder> {
    void onItemClick(VH holder, T item, int position);
}
