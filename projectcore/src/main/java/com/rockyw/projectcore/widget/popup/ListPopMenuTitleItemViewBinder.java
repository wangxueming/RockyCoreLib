package com.rockyw.projectcore.widget.popup;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rockyw.core.adapter.BaseItemViewBinder;
import com.rockyw.projectcore.R;
import com.rockyw.projectcore.R2;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/4/8
 */
public class ListPopMenuTitleItemViewBinder extends BaseItemViewBinder<ListPopupMenuBean.Title, ListPopMenuTitleItemViewBinder.ViewHolder> {

    public ListPopMenuTitleItemViewBinder(Context context) {
        super(context);
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.core_item_list_popup_menu_title, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull ListPopupMenuBean.Title item) {
        super.onBindViewHolder(holder, item);
        holder.titleTv.setText(item.titleText);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R2.id.core_tv_title)
        TextView titleTv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }


}
