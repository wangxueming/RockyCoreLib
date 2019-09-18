package com.rockyw.core.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;

import me.drakeet.multitype.ItemViewBinder;

/**
 * BaseItemViewBinder封装了MultiType的ItemViewBinder的单击和长按功能
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2018/12/28
 */
public abstract class BaseItemViewBinder<T, VH extends RecyclerView.ViewHolder> extends ItemViewBinder<T, VH> {

    public Context mContext;

    public BaseItemViewBinder(Context context) {
        this.mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    /**
     * 设置Item单击事件
     * @param onItemClickListener 被回调的listener
     */
    public void setOnItemClickListener(OnItemClickListener<T, VH> onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    /**
     * 设置Item长按事件
     * @param itemLongClickListener 被回调的listener
     */
    public void setOnItemLongClickListener(OnItemLongClickListener<T, VH> itemLongClickListener) {
        mOnItemLongClickListener = itemLongClickListener;
    }

    /**
     * 通用获取String方法
     * @param resId
     * @return
     */
    protected String getResString(int resId) {
        return getContext().getString(resId);
    }

    /**
     * 通用的获取Color方法
     * @param resId
     * @return
     */
    protected int getResColor(int resId) {
        return ContextCompat.getColor(getContext(), resId);
    }

    @Override
    protected void onBindViewHolder(@NonNull final VH holder, @NonNull final T item) {
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(v -> mOnItemClickListener.onItemClick(holder, item, holder.getAdapterPosition()));
        }
        if (mOnItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(v -> mOnItemLongClickListener.onItemLongClick(holder, item, holder.getAdapterPosition()));
        }
    }

}
