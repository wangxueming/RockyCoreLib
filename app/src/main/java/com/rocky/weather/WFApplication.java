package com.rocky.weather;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.umeng.commonsdk.UMConfigure;
import com.rocky.projectcore.common.router.RouterUrl;
import com.rocky.core.BaseApplication;
import com.rocky.core.LifecycleHandler;
import com.rocky.core.base.BaseEventBusBean;
import com.rocky.core.util.DimensionUtil;
import com.rocky.projectcore.UrlServiceManager;
import com.rocky.projectcore.login.IAccountService;
import com.rocky.router.WFRouter;

/**
 * 壳工程的Application
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2018/12/28
 */
public class WFApplication extends BaseApplication {

    @Autowired(name = RouterUrl.SERVER_ACCOUNT)
    IAccountService accountService;

    private static WFApplication mWFApplication;

    public static WFApplication getInstance() {
        return mWFApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mWFApplication = this;

        WFRouter.init(this, BuildConfig.DEBUG);

        AppConfigLauncher.init();

        //#个推的初始化 没用到之前先屏蔽
        // PushHelper.initPushService(this);

        registerActivityLifecycleCallbacks(new LifecycleHandler());

        //#配置账户状态监听
        registerAccountListener();

        //#配置umeng
        configUMeng();

        DimensionUtil.init(this);
    }

    @Override
    protected void onEvent(BaseEventBusBean event) {
        super.onEvent(event);
    }

    @Override
    public void doLogoutInApplication() {
        IAccountService accountService = UrlServiceManager.getAccountService();
        accountService.clearUserInfo(this);
        accountService.notifyLogout();
        accountService.startSplashPage();
    }

    private void configUMeng() {
        /**
         * 初始化common库
         * 参数1:上下文，不能为空
         * 参数2:设备类型，UMConfigure.DEVICE_TYPE_PHONE为手机、UMConfigure.DEVICE_TYPE_BOX为盒子，默认为手机
         * 参数3:Push推送业务的secret
         */
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "");
    }

    private void registerAccountListener() {
        accountService.registerObserver(new IAccountService.Observer() {
            @Override
            public void onLoginSuccess() {
            }

            @Override
            public void onLoginCancel() {
            }

            @Override
            public void onLoginFailure() {
            }

            @Override
            public void onLogout() {
                com.rocky.weather.net.Server.resetServerApi();
                AppConfigLauncher.reInit();
            }
        });
    }
}
