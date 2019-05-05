package com.rocky.net.v2.observer;

import com.rocky.core.widget.ILoadingView;

/**
 *
 *
 * @author wangxueming
 * @version 1.0.0
 * @date 2018/12/14
 */
public class LoadingViewConfig {

    public static final int TYPE_NONE = 0;
    /**
     * 下拉刷新
     */
    public static final int TYPE_PULL = 1;

    /**
     * 中间对话框（LoadingDialog ..）
     */
    public static final int TYPE_DIALOG = 2;

    /**
     * 圆形进度条（ProgressBar ..）
     */
    public static final int TYPE_PROGRESS = 3;

    ILoadingView mILoadingView;
    int type;

    /**
     * @param iLoadingView
     * @param type         true为覆盖式(例如dialog)，false反之(例如下拉刷新动画)
     */
    public LoadingViewConfig(ILoadingView iLoadingView, int type) {
        this.mILoadingView = iLoadingView;
        this.type = type;
    }

}
