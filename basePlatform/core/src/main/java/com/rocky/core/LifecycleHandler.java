package com.rocky.core;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.umeng.analytics.MobclickAgent;
import com.rocky.core.util.ActivityUtils;

/**
 *
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2018/12/28
 */
public class LifecycleHandler implements Application.ActivityLifecycleCallbacks {

    private static int resumed;
    private static int paused;
    private static int started;
    private static int stopped;

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        MobclickAgent.onPageStart(activity.getLocalClassName());
        ARouter.getInstance().inject(this);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        ActivityUtils.getInstance().pushActivity(activity);
        ++started;
    }

    @Override
    public void onActivityResumed(Activity activity) {
        MobclickAgent.onResume(activity);
        ++resumed;
    }

    @Override
    public void onActivityPaused(Activity activity) {
        MobclickAgent.onPause(activity);
        ++paused;
    }

    @Override
    public void onActivityStopped(Activity activity) {
        ActivityUtils.getInstance().removeActivity(activity);
        ++stopped;
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    public static boolean isApplicationVisible() {
        return started > stopped;
    }

    public static boolean isApplicationInForeground() {
        // 当所有 Activity 的状态中处于 resumed 的大于 paused 状态的，即可认为有Activity处于前台状态中
        return resumed > paused;
    }

}