package com.rocky.core.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.rocky.core.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 适用页面数量多的情况
 * <p>
 * 该 PagerAdapter 的实现将只保留当前页面，
 * 当页面离开视线后，就会被消除，释放其资源；
 * 而在页面需要显示时，生成新的页面(就像 ListView 的实现一样)。
 * 这么实现的好处就是当拥有大量的页面时，不必在内存中占用大量的内存。
 * 如果页面少，请使用BaseFragmentAdapter
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2018/12/11
 */
public class BaseFragmentStateAdapter extends FragmentStatePagerAdapter {

    List<Fragment> fragmentList = new ArrayList();
    private List<String> mTitles;

    public BaseFragmentStateAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    public BaseFragmentStateAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> mTitles) {
        super(fm);
        this.mTitles = mTitles;
        this.fragmentList = fragmentList;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return !ObjectUtils.isEmpty(mTitles) ? mTitles.get(position) : "";
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

}
