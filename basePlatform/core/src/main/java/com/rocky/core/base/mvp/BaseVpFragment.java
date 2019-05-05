package com.rocky.core.base.mvp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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
import com.rocky.core.base.mvc.BaseVcActivity;
import com.rocky.core.rx.RxCompositeMap;
import com.rocky.core.base.mvp.inter.IPresenter;
import com.rocky.core.base.mvp.inter.IView;
import com.rocky.core.base.mvp.inter.MvpCallback;
import com.rocky.core.util.ToastUtils;
import com.rocky.core.util.logger.L;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * MVP模式的Base fragment
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2018/12/24
 */
public abstract class BaseVpFragment<V extends IView, P extends IPresenter<V>> extends Fragment implements MvpCallback<V, P>, IView {

    private Unbinder unBinder;
    protected Context mContext;
    protected boolean isEventBusOn;
    protected P mPresenter;
    protected V mView;
    private BaseVpActivity mBaseActivity;
    public ImmersionBar mImmersionBar;

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
        init(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //#默认数据提前初始化
        initData();

        View view = inflater.inflate(getLayout(), container, false);
        unBinder = ButterKnife.bind(this, view);
        //沉浸式状态栏
        L.d("状态栏" + "initImmersionBar");
        initImmersionBar();
        onViewCreated();
        L.d("状态栏" + "initTitle");
        initTitle();

        if (isEventBusOn) {
            EventBus.getDefault().register(this);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mIsInitView = true;
        init(view);
        initView();
    }

    /**
     * 回传view
     *
     * @param view
     */
    protected void init(View view) {
    }

    /**
     * 初始化presenter
     */
    public void onViewCreated() {
        mView = createView();
        if (getPresenter() == null) {
            mPresenter = createPresenter();
            getLifecycle().addObserver(mPresenter);
        }
        mPresenter = getPresenter();
        //在这个时候才attach view是因为这个时候view的初始化已经基本完成,在Presenter中调用view的域也不会为空
        mPresenter.attachView(getMvpView());
    }

    //------------------------显示进度圈strat-----------------------------------------//

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
        if (getActivity() != null && mBaseActivity != null) {
            mBaseActivity.dismissHUD();
        }

        if (getActivity() instanceof BaseVpActivity) {
            BaseVpActivity baseActivity = (BaseVpActivity) getActivity();
            baseActivity.dismissHUD();
        }
        if (getActivity() instanceof BaseVcActivity) {
            BaseVcActivity baseActivity = (BaseVcActivity) getActivity();
            baseActivity.dismissHUD();
        }
    }
    //------------------------显示进度圈end-----------------------------------------//

    private void initImmersionBar() {
        if (mImmersionBar == null) {
            L.d("状态栏" + "initImmersionBar2");
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
        }
    }

    //------------------空界面显示START-------------------------//

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
    //------------------空界面显示END-------------------------//

    /**
     * eventBut
     *
     * @param event
     */
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

        //清理网络请求所用到的回调
        RxCompositeMap.getInstance().clear(this);
        setPresenter(null);
        setMvpView(null);

        if (isEventBusOn) {
            EventBus.getDefault().unregister(this);
        }

        //leakCanary 监控
        RefWatcher refWatcher = BaseApplication.getRefWatcher(mContext);
        refWatcher.watch(this);
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
    protected abstract int getLayout();

    /**
     * 初始化标题
     */
    protected abstract void initTitle();

    /**
     * 初始化数据
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected void initData() {
    }

    /**
     * view填充之前 过去Intent数据  绑定Presenter等
     * 注意:获取intent的数据需要在super之前,否则如果创建Presenter使用到这些数据的话,这些数据在使用时还未被赋值
     *
     * @param savedInstanceState
     */
    protected void init(Bundle savedInstanceState) {
    }

    /**
     * 通用的获取Color方法
     *
     * @param resId
     * @return
     */
    protected int getResColor(int resId) {
        return ContextCompat.getColor(getContext(), resId);
    }
}
