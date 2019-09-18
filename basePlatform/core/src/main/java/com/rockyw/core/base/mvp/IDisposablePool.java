package com.rockyw.core.base.mvp;

import io.reactivex.disposables.Disposable;

/**
 * RXJava 连接池
 *
 * @author wangxueming
 * @date 2018/12/13
 * @version 1.0.0
 */
public interface IDisposablePool {
    /**
     * RXJava管理订阅者
     *
     * @param disposable
     */
    void addDisposable(Disposable disposable);

    /**
     * 丢弃连接 在view销毁时调用,取消订阅关系
     */
    void clearPool();

}
