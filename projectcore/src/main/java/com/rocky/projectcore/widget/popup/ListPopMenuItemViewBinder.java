package com.rocky.projectcore.widget.popup;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.rocky.core.adapter.BaseItemViewBinder;
import com.rocky.projectcore.R;
import com.rocky.projectcore.R2;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/4/8
 */
public class ListPopMenuItemViewBinder extends BaseItemViewBinder<ListPopupMenuBean.Menu, ListPopMenuItemViewBinder.ViewHolder> {

    private PopupWindow mPopupWindow;

    public ListPopMenuItemViewBinder(Context context, PopupWindow popup) {
        super(context);
        this.mPopupWindow = popup;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.core_item_list_popup_menu, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, @NonNull final ListPopupMenuBean.Menu item) {
        super.onBindViewHolder(holder, item);
        holder.menuTv.setText(item.menuText);
        holder.menuTv.setTextColor(item.selectedIndex == holder.getAdapterPosition() ? ContextCompat.getColor(mContext, R.color.base_orange_F0932E) : ContextCompat.getColor(mContext, R.color.base_black_666666));
        if (item.menuIcon == 0) {
            holder.menuTv.setCompoundDrawables(null, null, null, null);
        } else {
            Drawable leftDrawable = ContextCompat.getDrawable(mContext, item.menuIcon);
            leftDrawable.setBounds(0, 0, leftDrawable.getMinimumWidth(), leftDrawable.getMinimumHeight());
            holder.menuTv.setCompoundDrawables(leftDrawable, null, null, null);
        }
        holder.menuTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.listener != null) {
                    item.listener.onClick(mPopupWindow, item.menuText, holder.getLayoutPosition());
                }
            }
        });
        holder.dividerView.setVisibility(item.showDivider ? View.VISIBLE : View.GONE);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R2.id.core_tv_menu)
        TextView menuTv;

        @BindView(R2.id.core_view_divider)
        View dividerView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }


}
