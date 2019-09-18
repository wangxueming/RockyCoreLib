package com.rockyw.core.base.bean;

import android.support.v4.app.Fragment;

/**
 * 单activity多Fragment组合时，每个标签的bean
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2018/12/24
 */
public class TabListBean {
    private String title;
    private Fragment fragment;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public TabListBean(String title, Fragment fragment) {
        this.title = title;
        this.fragment = fragment;
    }
}
