package com.rocky.core.widget.progress;

/**
 * 进度条变化监听类
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/4/17
 */
public interface OnProgressBarListener {

    /**
     * 进度变化
     * @param current
     * @param max
     */
    void onProgressChange(int current, int max);
}
