package com.rockyw.projectcore.widget.binder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rockyw.core.adapter.BaseItemViewBinder;
import com.rockyw.projectcore.bean.EmptyBean;

/**
 * 这是个空view binder，只用来展示 有UI变化，但是，不需要有什么内容变化的binder
 * 一般会通过在adapter add的时候，就设置listener，只增加点击的动作
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/4/8
 */
public abstract class BaseNoOperateItemViewBinder extends BaseItemViewBinder<EmptyBean, BaseNoOperateItemViewBinder.ViewHolder> {

    public BaseNoOperateItemViewBinder(Context context) {
        super(context);
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(getItemLayout(), parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull EmptyBean item) {
        super.onBindViewHolder(holder, item);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 获取view binder 的layout
     * @return
     */
    public abstract int getItemLayout();

}
