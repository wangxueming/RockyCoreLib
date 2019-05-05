package com.rocky.core.base.bean;

import com.rocky.core.base.mvc.BaseVcFragment;

/**
 *
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2018/12/24
 */
public class TabListVcBean {
    private String title;
    private BaseVcFragment fragment;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BaseVcFragment getFragment() {
        return fragment;
    }

    public void setFragment(BaseVcFragment fragment) {
        this.fragment = fragment;
    }

    public TabListVcBean(String title, BaseVcFragment fragment) {
        this.title = title;
        this.fragment = fragment;
    }
}
