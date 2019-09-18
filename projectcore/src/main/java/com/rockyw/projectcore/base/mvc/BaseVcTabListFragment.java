package com.rockyw.projectcore.base.mvc;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.rockyw.projectcore.R;
import com.rockyw.projectcore.R2;
import com.rockyw.core.base.bean.TabListBean;
import com.rockyw.core.base.mvc.BaseVcFragment;
import com.rockyw.customview.widget.WinTopBar;

import java.util.List;

import butterknife.BindView;

/**
 * 自带
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2018/12/24
 */
public abstract class BaseVcTabListFragment extends BaseVcFragment implements View.OnClickListener {

    @BindView(R2.id.core_top_bar)
    public WinTopBar mTopBar;
    @BindView(R2.id.core_tl_tabs)
    public TabLayout mTlTabs;
    @BindView(R2.id.core_vp_view)
    public ViewPager mVpView;
    private List<TabListBean> titleList;
    protected TabAdapter tabAdapter;

    @Override
    protected int getLayout() {
        titleList = tabTitles();
        return R.layout.base_frag_tab_list;
    }

    @Override
    protected void initTitle() {
        mTopBar.setTitleText(getTitle());
    }

    @Override
    protected void initView() {
        mVpView.setAdapter(tabAdapter = new TabAdapter(getChildFragmentManager()));
        mTlTabs.setTabMode(isTabScrollable() ? TabLayout.MODE_SCROLLABLE : TabLayout.MODE_FIXED);
        mTlTabs.setupWithViewPager(mVpView);
    }

    @Override
    public void onClick(View v) {
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

    /**
     * tab标题集合
     *
     * @return
     */
    protected abstract List<TabListBean> tabTitles();

    /**
     * 设置标题
     *
     * @return
     */
    protected abstract String getTitle();

    protected boolean isTabScrollable() {
        return false;
    }
}
