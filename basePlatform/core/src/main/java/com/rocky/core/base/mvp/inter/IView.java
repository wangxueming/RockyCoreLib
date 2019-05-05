package com.rocky.core.base.mvp.inter;

import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;

import com.rocky.core.widget.ILoadingView;

/**
 * 顶级view接口
 *
 * @author wangxueming
 * @version 1.0.0
 * @date 2018/12/13
 */
public interface IView extends LifecycleOwner {

    Context getContext();

    /**
     * 展示网络请求错误,弹框提示
     *
     * @param msg
     * @param code
     */
    void showError(String msg, String code);

    /**
     * 展示网络请求中的弹框提示
     */
    void showHUD(String msg);

    /**
     * 关闭网络请求的弹框提示
     */
    void dismissHUD();

    //----------------------------下面用来显示空界面---------------------------//

    /**
     * showNormal 页面
     */
    void showNormal();

    /**
     *
     */
    void showLoading();
    /**
     * Show EmptyView 页面
     */
    void showEmptyView();

    /**
     * Show error 页面
     */
    void showError();
}
