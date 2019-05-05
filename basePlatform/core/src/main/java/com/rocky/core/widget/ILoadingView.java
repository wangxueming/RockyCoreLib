package com.rocky.core.widget;

/**
 * 加载进行网络请求时，页面上弹出的加载动画
 *
 * @author: wangxueming
 * @version: 1.0.0
 * @date: 2018/12/14
 */
public interface ILoadingView {

    /**
     * @param type 根据type判断是哪种加载动画类型
     *             LoadingViewConfig.TYPE_PULL
     *             LoadingViewConfig.TYPE_DIALOG ...
     */
    void showLoadingView(int type);

    /**
     * @param type 根据type判断是哪种加载动画类型
     *             LoadingViewConfig.TYPE_PULL
     *             LoadingViewConfig.TYPE_DIALOG ...
     */
    void hideLoadingView(int type);

}

