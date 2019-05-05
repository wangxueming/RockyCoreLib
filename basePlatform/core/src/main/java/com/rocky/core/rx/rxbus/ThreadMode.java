package com.rocky.core.rx.rxbus;

/**
 * 线程枚举
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/4/17
 */
public enum ThreadMode {
    /**
     * current thread
     */
    CURRENT_THREAD,

    /**
     * android main thread
     */
    MAIN,


    /**
     * new thread
     */
    NEW_THREAD
}
