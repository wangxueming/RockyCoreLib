package com.rockyw.projectcore.widget.popup;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.rockyw.projectcore.R;
import com.rockyw.projectcore.R2;

import java.util.Iterator;

import butterknife.BindView;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 *
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/4/9
 */
public class ListMenuPop extends AbstractCommonPop {

    @BindView(R2.id.core_rv_menu)
    RecyclerView mRecyclerView;

    private MultiTypeAdapter mAdapter;
    private Items mItems = new Items();

    private int mSelectedPosition = 0;

    private ListMenuPop(Context context) {
        super(context);
        init();
    }

    @Override
    public int getLayoutId() {
        return R.layout.core_pop_list_menu;
    }

    public void select(int position) {
        if (mItems.isEmpty()) {
            return;
        }
        mSelectedPosition = position;
        Iterator<Object> iter = mItems.iterator();
        while (iter.hasNext()) {
            Object item = iter.next();
            if (item instanceof ListPopupMenuBean.Menu) {
                ListPopupMenuBean.Menu myBean = (ListPopupMenuBean.Menu) item;
                myBean.selectedIndex = position;
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    public int getSelectedPosition() {
        return mSelectedPosition;
    }

    public int getItemSize() {
        return mItems.size();
    }

    public static class Builder {

        private ListMenuPop mPop;

        public Builder(Context context) {
            mPop = new ListMenuPop(context);
        }

        public Builder addTitle(ListPopupMenuBean.Title title) {
            if (title != null) {
                mPop.add(title);
            }
            return this;
        }

        public Builder addMenu(ListPopupMenuBean.Menu menu) {
            if (menu != null) {
                mPop.add(menu);
            }
            return this;
        }

        public Builder setOnClickListener() {
            return this;
        }

        public ListMenuPop build() {
            mPop.notifyDataSetChanged();
            // 最后一个菜单不显示底部分割线
            if (!mPop.mItems.isEmpty() && mPop.mItems.get(mPop.mItems.size() - 1) instanceof ListPopupMenuBean.Menu) {
                ListPopupMenuBean.Menu lastMenu = (ListPopupMenuBean.Menu) mPop.mItems.get(mPop.mItems.size() - 1);
                lastMenu.showDivider = false;
            }
            return mPop;
        }
    }

    public void updateMenu(int pos, String text) {
        getMenu(pos).setMenuText(text);
        notifyDataSetChanged();
    }

    public void updateTitle(int pos, String text) {
        getTitle(pos).setTitleText(text);
        notifyDataSetChanged();
    }

    private ListPopupMenuBean.Menu getMenu(int pos) {
        return (ListPopupMenuBean.Menu) mItems.get(pos);
    }

    private ListPopupMenuBean.Title getTitle(int pos) {
        return (ListPopupMenuBean.Title) mItems.get(pos);
    }

    private void init() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new MultiTypeAdapter(mItems);
        mAdapter.register(ListPopupMenuBean.Title.class, new ListPopMenuTitleItemViewBinder(getContext()));
        mAdapter.register(ListPopupMenuBean.Menu.class, new ListPopMenuItemViewBinder(getContext(), this));
        mRecyclerView.setAdapter(mAdapter);
    }

    private void add(Object o) {
        mItems.add(o);
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
    }

    private void notifyDataSetChanged() {
        mAdapter.notifyDataSetChanged();
    }

}