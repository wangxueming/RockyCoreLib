package com.rocky.projectcore.base.mvc;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;

import com.rocky.projectcore.R;
import com.rocky.projectcore.R2;
import com.rocky.core.base.bean.TabListVcBean;
import com.rocky.core.base.mvc.BaseVcActivity;
import com.rocky.customview.widget.WinTopBar;

import java.util.List;

import butterknife.BindView;

/**
 * 标签列表的基类
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/1/15
 */
public abstract class BaseVcTabListActivity extends BaseVcActivity {
    
    @BindView(R2.id.core_top_bar)
    WinTopBar mTopBar;
    @BindView(R2.id.core_tl_tabs)
    TabLayout mTlTabs;
    @BindView(R2.id.core_vp_view)
    ViewPager mVpView;
    @BindView(R2.id.layout_frag_tab_list)
    LinearLayout mLayoutFragTabList;

    private List<TabListVcBean> titleList;
    protected TabAdapter tabAdapter;

    @Override
    protected int getLayoutId() {
        titleList = tabTitles();
        return R.layout.base_frag_tab_list;
    }

    @Override
    protected void initTitle() {
        mLayoutFragTabList.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.windowBg));
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
    protected abstract List<TabListVcBean> tabTitles();

    /**
     * 设置标题
     *
     * @return
     */
    protected abstract String setTitle();
}
