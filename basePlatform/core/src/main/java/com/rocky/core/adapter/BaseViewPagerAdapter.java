package com.rocky.core.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 仅适合少量Fragment的添加。每个Fragment都会被添加进内存。
 * <p>
 * 所以，涉及到Fragment需要添加较多，有不可见的Fragment。不建议用这个
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2018/12/28
 */
public class BaseViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();

    public BaseViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    /**
     * 手动添加Fragment方法
     * @param fragment 待添加的Fragment实例
     */
    public void addFragment(Fragment fragment) {
        mFragmentList.add(fragment);
    }

}
