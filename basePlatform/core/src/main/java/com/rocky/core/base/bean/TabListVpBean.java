package com.rocky.core.base.bean;

import com.rocky.core.base.mvp.BaseVpFragment;

/**
 *
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2018/12/24
 */
public class TabListVpBean {
    private String title;
    private BaseVpFragment fragment;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BaseVpFragment getFragment() {
        return fragment;
    }

    public void setFragment(BaseVpFragment fragment) {
        this.fragment = fragment;
    }

    public TabListVpBean(String title, BaseVpFragment fragment) {
        this.title = title;
        this.fragment = fragment;
    }
}
