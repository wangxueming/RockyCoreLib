package com.rocky.core.base.mvp.inter;

/**
 * mvp模式抽象vp的解绑和绑定过程
 *
 * @author wangxueming
 * @version 1.0.0
 * @date 2018/12/13
 */
public interface MvpCallback<V extends IView, P extends IPresenter<V>> {
    /**
     * 创建Presenter  调用在init中
     */
    P createPresenter();

    /**
     * 创建View
     * @return
     */
    V createView();

    void setPresenter(P presenter);

    P getPresenter();

    void setMvpView(V view);

    V getMvpView();
}
