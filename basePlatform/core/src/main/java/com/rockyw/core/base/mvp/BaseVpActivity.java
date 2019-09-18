package com.rockyw.core.base.mvp;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.gyf.barlibrary.ImmersionBar;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.orhanobut.logger.Logger;
import com.rockyw.core.BaseApplication;
import com.rockyw.core.R;
import com.rockyw.core.annotation.winconfig.WinConfigProcess;
import com.rockyw.core.base.BaseEventBusBean;
import com.rockyw.core.base.mvp.inter.IPresenter;
import com.rockyw.core.base.mvp.inter.IView;
import com.rockyw.core.base.mvp.inter.MvpCallback;
import com.rockyw.core.helper.HUDFactory;
import com.rockyw.core.rx.RxCompositeMap;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import pl.droidsonroids.gif.GifDrawable;

/**
 * MVP模式的Base Activity
 *
 * @author wangxueming
 * @version 1.0.0
 * @date 2018/12/13
 */
public abstract class BaseVpActivity<V extends IView, P extends IPresenter<V>> extends AppCompatActivity implements MvpCallback<V, P>, IView {

    protected Fragment currentFragment;
    /**
     * ButterKnife的UnBinder
     */
    private Unbinder unBinder;
    /**
     * 是否使用EventBus的Switcher。默认为关闭
     */
    protected boolean isEventBusOn;
    /**
     * 网络请求相关的弹框提示
     */
    public KProgressHUD kProgressHUD;
    /**
     * 沉浸式的状态栏
     */
    public ImmersionBar mImmersionBar;

    private P mPresenter;
    private V mView;
    protected boolean isDestroy = false;
    private GifDrawable gifFromResource;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        WinConfigProcess.bind(this);

        //#默认数据提前初始化
        initData();

        setContentView(getLayoutId());

        //初始化ButterKnife
        unBinder = ButterKnife.bind(this);
        //加入activity管理
        BaseApplication.getAppContext().getActivityControl().addActivity(this);
        //沉浸式状态栏
        initImmersionBar();
        //#EventBus
        if (isEventBusOn) {
            EventBus.getDefault().register(this);
        }

        //初始化阶段
        onViewCreated();
        initTitle();
        initView();
        initListener();
        onLazyLoad();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.i("当前运行的activity:" + getClass().getName());
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        super.onDestroy();

        //解绑ButterKnife
        if (unBinder != null) {
            unBinder.unbind();
        }
        //跟Presenter的引用清理
        setPresenter(null);
        //跟View的引用清理
        setMvpView(null);
        //EventBus取消注册
        if (isEventBusOn) {
            EventBus.getDefault().unregister(this);
        }
        //销毁沉浸式状态栏
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
        }
        isDestroy = true;
        //关闭HUD
        dismissHUD();
        //清理网络请求所用到的回调
        RxCompositeMap.getInstance().clear(this);
        //移除运行中的类
        BaseApplication.getAppContext().getActivityControl().removeActivity(this);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @CallSuper
    protected void initListener() {
        mPresenter.attachView(getMvpView());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(BaseEventBusBean event) {
        onEvent(event);
    }

    protected void onEvent(BaseEventBusBean event) {
    }

    @Override
    public void showHUD(String msg) {
        if (isDestroy) {
            return;
        }
        if (kProgressHUD == null) {
            kProgressHUD = HUDFactory.getInstance().createHUD(this);
        }
        try {
            gifFromResource = new GifDrawable(getResources(), R.drawable.base_loading_wj);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageView imageView = new ImageView(getContext());
        imageView.setBackground(gifFromResource);
        kProgressHUD.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                // .setLabel(getString(R.string.loading))
                .setBackgroundColor(Color.TRANSPARENT)
                .setLabel("")
                .setCustomView(imageView)
                // .setLabel(null)
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.3f).show();
    }

    @Override
    public void dismissHUD() {
        if (null != kProgressHUD && kProgressHUD.isShowing()) {
            kProgressHUD.dismiss();
        }
    }

    /**
     * 空界面显示
     */
    @Override
    public void showNormalView() {
    }

    @Override
    public void showEmptyView() {
    }

    @Override
    public void showLoadingView() {
    }

    @Override
    public void showNoNetView() {
    }

    @Override
    public P getPresenter() {
        return mPresenter;
    }

    @Override
    public void setPresenter(P presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void setMvpView(V view) {
        this.mView = view;
    }

    @Override
    public V getMvpView() {
        return this.mView;
    }

    /**
     * 获取当前Activity的UI布局
     *
     * @return 布局id
     */
    protected abstract int getLayoutId();

    /**
     * 初始化可能需要提前添加的数据，比如一些静态的数据。场景例如：我的页面
     */
    protected void initData() {
    }

    /**
     * 初始化标题
     */
    protected abstract void initTitle();

    /**
     * 初始化数据
     */
    protected abstract void initView();

    /**
     * 懒加载数据
     */
    protected void onLazyLoad() {}

    /**
     * 通用的获取Color方法
     *
     * @param resId
     * @return
     */
    protected int getResColor(int resId) {
        return ContextCompat.getColor(getContext(), resId);
    }

    private void initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this);
        //所有子类都将继承这些相同的属性
        mImmersionBar.fitsSystemWindows(true).statusBarColor(R.color.base_colorPrimary).init();
    }

    /**
     * 初始化presenter
     */
    private void onViewCreated() {
        mView = createView();
        if (getPresenter() == null) {
            mPresenter = createPresenter();
            //注册lifecycle生命周期
            getLifecycle().addObserver(mPresenter);
        }
        mPresenter = getPresenter();
        mPresenter.attachView(getMvpView());
    }
}
