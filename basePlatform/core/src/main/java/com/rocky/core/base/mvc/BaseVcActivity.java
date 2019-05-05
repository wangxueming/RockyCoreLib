package com.rocky.core.base.mvc;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.gyf.barlibrary.ImmersionBar;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.orhanobut.logger.Logger;
import com.rocky.core.BaseApplication;
import com.rocky.core.R;
import com.rocky.core.base.BaseEventBusBean;
import com.rocky.core.base.mvp.inter.IView;
import com.rocky.core.helper.HUDFactory;
import com.rocky.core.rx.RxCompositeMap;
import com.rocky.core.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import pl.droidsonroids.gif.GifDrawable;

/**
 * MVC模式的状态栏，即最普通的方式，最开始我们认为Activity其实就是V+C
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2018/12/26
 */
public abstract class BaseVcActivity extends AppCompatActivity implements IView {

    /**
     * ButterKnife的UnBinder
     */
    private Unbinder unBinder;
    /**
     * 网络请求相关的弹框提示
     */
    public KProgressHUD kProgressHUD;
    /**
     * 管理事件流订阅的生命周期CompositeDisposable
     */
    private CompositeDisposable compositeDisposable;
    /**
     * 沉浸式的状态栏
     */
    public ImmersionBar mImmersionBar;

    /**
     * 是否使用EventBus的Switcher。默认为关闭
     */
    protected boolean isEventBusOn;

    protected boolean isDestroyed = false;
    private GifDrawable gifFromResource;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //#默认数据先初始化
        initData();

        setContentView(getLayoutId());

        //初始化ButterKnife
        unBinder = ButterKnife.bind(this);
        //加入activity管理
        BaseApplication.getAppContext().getActivityControl().addActivity(this);
        //沉浸式状态栏
        initImmersionBar();
        //#注册EventBus
        if (isEventBusOn) {
            EventBus.getDefault().register(this);
        }

        //初始化阶段回调
        initTitle();
        initView();
        onLazyLoad();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.i("当前运行的activity:" + getClass().getName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isDestroyed = true;
        //解除订阅关系
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
        //解绑ButterKnife
        if (unBinder != null) {
            unBinder.unbind();
        }
        //EventBus取消注册
        if (isEventBusOn) {
            EventBus.getDefault().unregister(this);
        }
        //销毁沉浸式状态栏
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
        }
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

    /**
     * RxJava管理订阅者
     */
    protected void addDisposable(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(BaseEventBusBean event) {
        onEvent(event);
    }

    protected void onEvent(BaseEventBusBean event) {
    }

    @Override
    public void showHUD(String msg) {
        if (isDestroyed) {
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
     * 提示网络请求错误信息
     *
     * @param msg
     * @param code
     */
    @Override
    public void showError(String msg, String code) {
        String mCode = "-1";
        if (mCode.equals(code)) {
            ToastUtils.showShort(getBaseContext(), msg);
        }
    }

    /**
     * 空界面显示
     */
    @Override
    public void showNormal() {
    }

    @Override
    public void showEmptyView() {
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void showError() {
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
        // 所有子类都将继承这些相同的属性,暂时先不加,会导入全部状态栏都一致
        mImmersionBar.fitsSystemWindows(true).statusBarColor(R.color.base_colorPrimary).init();
    }
}
