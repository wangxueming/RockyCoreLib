package com.rockyw.core;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Build;
import android.support.multidex.MultiDex;
import android.support.v4.content.LocalBroadcastManager;

import com.facebook.stetho.Stetho;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tencent.bugly.crashreport.CrashReport;
import com.rockyw.core.base.BaseEventBusBean;
import com.rockyw.core.constant.AppConfig;
import com.rockyw.core.util.AppUtils;
import com.rockyw.core.util.logger.L;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.joor.Reflect;

/**
 * @author wangxueming
 * @date 2018/12/12 下午4:34
 * @desc 基础Application，调整过了一些
 */
public class BaseApplication extends Application {
    public static boolean IS_DEBUG = BuildConfig.DEBUG;
    private static final String LOGOUT_ACTION = "com.rockyw.weather.logout";

    private static BaseApplication mBaseApplication;
    private RefWatcher refWatcher;
    /**
     * Activity管理
     */
    private ActivityControl mActivityControl;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(LOGOUT_ACTION)) {
                doLogoutInApplication();
            }
        }
    };

    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                //全局设置主题颜色
                layout.setPrimaryColorsId(R.color.base_colorPrimary, android.R.color.white);
                return new ClassicsHeader(context);
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    private IntentFilter mFilter;
    private LocalBroadcastManager mLocalBroadcastManager;

    public static RefWatcher getRefWatcher(Context context) {
        BaseApplication leakApplication = (BaseApplication) context.getApplicationContext();
        return leakApplication.refWatcher;
    }

    public static BaseApplication getAppContext() {
        return mBaseApplication;
    }

    public ActivityControl getActivityControl() {
        return mActivityControl;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mActivityControl = new ActivityControl();

        EventBus.getDefault().register(this);
        registerLocalBroadcast();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            closeAndroidPDialog();
        }

        configBugly();
        configLeakCanary();
        configStetho();
        configLogger();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        unRegisterLocalBroadcast();
        exitApp();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        mBaseApplication = this;
        //MultiDex分包方法 必须最先初始化
        MultiDex.install(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(BaseEventBusBean event) {
        onEvent(event);
    }

    protected void onEvent(BaseEventBusBean event) {
    }

    protected void doLogoutInApplication(){}

    /**
     * 退出应用
     */
    private void exitApp() {
        mActivityControl.finishiAll();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    private void configBugly() {
        String packageName = getApplicationContext().getPackageName();
        String processName = AppUtils.getProcessName(android.os.Process.myPid());

        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(getApplicationContext());
        strategy.setUploadProcess(processName == null || processName.equals(packageName));

        String buglyId = BuildConfig.DEBUG ? AppConfig.BUGLY_ID_DEBUG : AppConfig.BUGLY_ID;
        CrashReport.initCrashReport(getApplicationContext(), buglyId, false, strategy);
    }

    private void configLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        refWatcher = LeakCanary.install(this);
    }

    private void configStetho() {
        Stetho.initializeWithDefaults(this);
    }

    private void configLogger() {
        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
        L.i("当前是否为debug模式：" + IS_DEBUG);
    }

    private void registerLocalBroadcast() {
        mFilter = new IntentFilter();
        mFilter.addAction(LOGOUT_ACTION);
        //获得实例
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        //注册监听
        mLocalBroadcastManager.registerReceiver(receiver, mFilter);
    }

    private void unRegisterLocalBroadcast() {
        //取消监听，注意unregisterReceiver()方法来自LocalBroadcastManager;
        mLocalBroadcastManager.unregisterReceiver(receiver);
    }

    private void closeAndroidPDialog() {
        try {
            Class aClass = Class.forName("android.content.pm.PackageParser$Package");
            Constructor declaredConstructor = aClass.getDeclaredConstructor(String.class);
            declaredConstructor.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Class cls = Class.forName("android.app.ActivityThread");
            Method declaredMethod = cls.getDeclaredMethod("currentActivityThread");
            declaredMethod.setAccessible(true);
            Object activityThread = declaredMethod.invoke(null);
            Field mHiddenApiWarningShown = cls.getDeclaredField("mHiddenApiWarningShown");
            mHiddenApiWarningShown.setAccessible(true);
            mHiddenApiWarningShown.setBoolean(activityThread, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Reflect.on("android.app.ActivityThread").create().set("mHiddenApiWarningShown", true);
    }
}
