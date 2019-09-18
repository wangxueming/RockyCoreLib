package com.rockyw.core.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;

import com.rockyw.core.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 适用于那些相对静态的页，数量也比较少的那种
 * <p>
 * 该类内的每一个生成的 Fragment 都将保存在内存之中，
 * 如果需要处理有很多页，并且数据动态性较大、占用内存较多的情况，
 * 应该使用FragmentStatePagerAdapter。
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2018/12/21
 */
public class BaseFragmentAdapter extends FragmentPagerAdapter {

    List<Fragment> fragmentList = new ArrayList();
    private List<String> mTitles;

    public BaseFragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    public BaseFragmentAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> mTitles) {
        super(fm);
        this.mTitles = mTitles;
        setFragments(fm, fragmentList, mTitles);
    }

    /**
     * 刷新fragment
     * @param fm
     * @param fragments
     * @param mTitles
     */
    public void setFragments(FragmentManager fm, List<Fragment> fragments, List<String> mTitles) {
        this.mTitles = mTitles;
        if (this.fragmentList != null) {
            FragmentTransaction ft = fm.beginTransaction();
            for (Fragment f : this.fragmentList) {
                ft.remove(f);
            }
            ft.commitAllowingStateLoss();
            ft = null;
            fm.executePendingTransactions();
        }
        this.fragmentList = fragments;
        notifyDataSetChanged();
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
