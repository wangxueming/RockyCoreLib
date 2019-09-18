package com.rockyw.core.base.mvp.inter;

import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;

/**
 * 顶级view接口
 *
 * @author wangxueming
 * @version 1.0.0
 * @date 2018/12/13
 */
public interface IView extends LifecycleOwner {
    /**
     * 获取View载体的Context
     * @return
     */
    Context getContext();

    /**
     * 展示网络请求中的弹框提示
     *
     *  @param msg
     */
    void showHUD(String msg);

    /**
     * 关闭网络请求的弹框提示
     */
    void dismissHUD();

    /**
     * -以下是关于页面展示状态的变换-
     * 我认为对于一般app开发有三种状态：Normal(正常展示)、Empty(空数据)、NoNet(无网络)、Loading(读取中)
     * 前三种都清楚。为什么有Loading状态？能不能没有呢？
     * 一般来说，网络加载的UI展示方式有：转圈的菊花图、用List的HeaderView或者FootView做状态展示
     * 菊花图：展示的时候，是加了一层蒙板，不可操作；
     * 用List的HeaderView或者FootView做状态展示：展示在列表头尾，拖动则消失。
     * 为了应对一边操作View，一边又进行加载的情况。
     * 加入了Loading。比如说  知乎的加载。他其实是可以拖动的。而且常驻在最上层。最终的结果又通过List的头或尾 展示出来。
     */
    /**
     * 正常界面显示
     */
    void showNormalView();

    /**
     * 页面上有加载状态的展示
     */
    void showLoadingView();
    /**
     * 空界面展示
     */
    void showEmptyView();

    /**
     * 网络异常页面展示
     */
    void showNoNetView();
}
