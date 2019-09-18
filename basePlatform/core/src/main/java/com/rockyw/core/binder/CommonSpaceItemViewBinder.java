package com.rockyw.core.binder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Space;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rockyw.core.R;
import com.rockyw.core.R2;
import com.rockyw.core.adapter.BaseItemViewBinder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 空Item
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/4/17
 */
public class CommonSpaceItemViewBinder extends BaseItemViewBinder<Integer, CommonSpaceItemViewBinder.ViewHolder> {

    public CommonSpaceItemViewBinder(Context context) {
        super(context);
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.base_item_common_space, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Integer height) {
        super.onBindViewHolder(holder, height);
        ViewGroup.LayoutParams params = holder.spaceView.getLayoutParams();
        params.height = height;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R2.id.view_space)
        Space spaceView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
