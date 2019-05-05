package com.rocky.core.base.mvc;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.gyf.barlibrary.ImmersionBar;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.RefWatcher;
import com.rocky.core.BaseApplication;
import com.rocky.core.R;
import com.rocky.core.base.BaseEventBusBean;
import com.rocky.core.rx.RxCompositeMap;
import com.rocky.core.base.mvp.BaseVpActivity;
import com.rocky.core.base.mvp.inter.IView;
import com.rocky.core.util.ToastUtils;
import com.rocky.core.util.logger.L;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * MVC模式的Base fragment
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/4/17
 */
public abstract class BaseVcFragment extends Fragment implements IView {

    protected Context mContext;
    /**
     * ButterKnife的UnBinder
     */
    private Unbinder unBinder;
    /**
     * 是否使用EventBus的Switcher。默认为关闭
     */
    protected boolean isEventBusOn;
    /**
     * 沉浸式的状态栏
     */
    public ImmersionBar mImmersionBar;
    /**
     * 管理事件流订阅的生命周期CompositeDisposable
     */
    private CompositeDisposable compositeDisposable;

    protected boolean mIsInitView = false;
    protected boolean mIsVisible = false;
    protected boolean mIsLazyLoaded = false;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //#默认数据提前初始化
        initData();

        View view = inflater.inflate(getLayout(), container, false);
        unBinder = ButterKnife.bind(this, view);
        //沉浸式状态栏
        initImmersionBar();
        initTitle();

        if (isEventBusOn) {
            EventBus.getDefault().register(this);
        }
        return view;
    }

    /**
     * 返回view
     *
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mIsInitView = true;
        initView();
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

    @Override
    public void showHUD(String msg) {
        if (getActivity() != null) {
            if (getActivity() instanceof BaseVpActivity) {
                BaseVpActivity baseActivity = (BaseVpActivity) getActivity();
                baseActivity.showHUD(msg);
            }
            if (getActivity() instanceof BaseVcActivity) {
                BaseVcActivity baseActivity = (BaseVcActivity) getActivity();
                baseActivity.showHUD(msg);
            }
        }
    }

    @Override
    public void dismissHUD() {
        if (getActivity() != null) {
            if (getActivity() instanceof BaseVpActivity) {
                BaseVpActivity baseActivity = (BaseVpActivity) getActivity();
                baseActivity.dismissHUD();
            } else if (getActivity() instanceof BaseVcActivity) {
                BaseVcActivity baseActivity = (BaseVcActivity) getActivity();
                baseActivity.dismissHUD();
            }
        }
    }

    private void initImmersionBar() {
        if (mImmersionBar == null) {
            mImmersionBar = ImmersionBar.with(this);
            // 所有子类都将继承这些相同的属性,暂时先不加,会导入全部状态栏都一致
            mImmersionBar.fitsSystemWindows(true).statusBarColor(R.color.base_colorPrimary).init();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Logger.i("当前运行的fragment:" + getClass().getName());
        // 解决第一个 Fragment 的 onLazyLoad 不调用的问题
        if (mIsVisible && mIsInitView && !mIsLazyLoaded) {
            lazyLoad();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mIsInitView = false;
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
     * 提示网络请求错误信息
     *
     * @param msg
     * @param code
     */
    @Override
    public void showError(String msg, String code) {
        String mCode = "-1";
        if (mCode.equals(code)) {
            ToastUtils.showShort(mContext, msg);
            showError();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(BaseEventBusBean event) {
        onEvent(event);
    }

    protected void onEvent(BaseEventBusBean event) {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unBinder != null) {
            unBinder.unbind();
        }
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
        }
        if (isEventBusOn) {
            EventBus.getDefault().unregister(this);
        }

        //清理网络请求所用到的回调
        RxCompositeMap.getInstance().clear(this);
        //leakCanary 监控
        RefWatcher refWatcher = BaseApplication.getRefWatcher(mContext);
        refWatcher.watch(this);
    }

    /**
     * 获取当前Activity的UI布局
     *
     * @return 布局id
     */
    protected abstract int getLayout();

    /**
     * 初始化标题
     */
    protected abstract void initTitle();

    /**
     * 初始化数据
     */
    protected void initData(){
    }

    /**
     * 初始化数据
     */
    protected abstract void initView();

    protected int getResColor(int resId) {
        return ContextCompat.getColor(getContext(), resId);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        L.e("setUserVisibleHint: " + isVisibleToUser);
        mIsVisible = isVisibleToUser;
        if (mIsVisible && mIsInitView && !mIsLazyLoaded) {
            lazyLoad();
            return;
        }

        if (mIsVisible && mIsInitView && mIsLazyLoaded) {
            onResumeAfterLazyLoad();
        }
    }

    private void lazyLoad() {
        lazyLoadData();
        mIsLazyLoaded = true;
    }
    /**
     * 懒加载方法
     */
    protected void lazyLoadData() {
    }

    /**
     * 如字面意思
     */
    protected void onResumeAfterLazyLoad(){
    }
}
