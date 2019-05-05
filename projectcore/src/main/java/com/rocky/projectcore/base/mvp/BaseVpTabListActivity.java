package com.rocky.projectcore.base.mvp;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.rocky.projectcore.R;
import com.rocky.projectcore.R2;
import com.rocky.core.base.bean.TabListVpBean;
import com.rocky.core.base.mvp.BaseVpActivity;
import com.rocky.core.base.mvp.inter.IPresenter;
import com.rocky.core.base.mvp.inter.IView;
import com.rocky.customview.widget.WinTopBar;

import java.util.List;

import butterknife.BindView;

/**
 * 基础mvp形式的带Tab的list
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/4/4
 */
public abstract class BaseVpTabListActivity<V extends IView, P extends IPresenter<V>> extends BaseVpActivity<V, P> {

    @BindView(R2.id.core_top_bar)
    WinTopBar mTopBar;
    @BindView(R2.id.core_tl_tabs)
    TabLayout mTlTabs;
    @BindView(R2.id.core_vp_view)
    ViewPager mVpView;

    private List<TabListVpBean> titleList;
    protected TabAdapter tabAdapter;

    @Override
    protected int getLayoutId() {
        titleList = tabTitles();
        return R.layout.base_frag_tab_list;
    }

    @Override
    protected void initTitle() {
        if (titleList == null || titleList.size() == 0) {
            return;
        }
        int size = titleList.size();
        for (int i = 0; i < size; i++) {
            mTlTabs.addTab(mTlTabs.newTab().setText(titleList.get(i).getTitle()));
        }
        mVpView.setOffscreenPageLimit(size);
        mTopBar.setTitleText(setTitle());
    }

    @Override
    protected void initView() {
        mVpView.setAdapter(tabAdapter = new TabAdapter(getSupportFragmentManager()));
        mTlTabs.setTabMode(isTabScrollable() ? TabLayout.MODE_SCROLLABLE : TabLayout.MODE_FIXED);
        mTlTabs.setupWithViewPager(mVpView);
    }

    class TabAdapter extends FragmentPagerAdapter {
        public TabAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return titleList.get(position).getFragment();
        }

        @Override
        public int getCount() {
            return titleList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position).getTitle();
        }
    }

    protected boolean isTabScrollable() {
        return false;
    }

    /**
     * tab标题集合
     *
     * @return
     */
    protected abstract List<TabListVpBean> tabTitles();

    /**
     * 设置标题
     *
     * @return
     */
    public abstract String setTitle();
}
