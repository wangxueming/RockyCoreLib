package com.rocky.core.base.mvp;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 管理RXJava订阅关系,实现了连接丢弃  不继承这个类的model 要手动实现 添加和清空
 *
 * @author wangxueming
 * @date 2018/12/13
 * @version 1.0.0
 */
public class DisposablePoolImpl implements IDisposablePool{

    /**
     * 管理RXJava订阅关系
     * 暂时没用
     */
    private CompositeDisposable mDisposable;

    @Override
    public void addDisposable(Disposable disposable) {
        if (mDisposable == null) {
            mDisposable = new CompositeDisposable(disposable);
        } else {
            mDisposable.add(disposable);
        }
    }

    @Override
    public void clearPool() {
        if (mDisposable != null) {
            mDisposable.clear();
            mDisposable = null;
        }
    }
}
