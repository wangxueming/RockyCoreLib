package com.rockyw.core.util;

import android.app.Activity;
import android.content.Context;

import com.rockyw.core.util.logger.L;

import java.util.Stack;

/**
 * activity 工具类，用来记录压入栈内的activity状态
 * mAppContext，但凡你调用一次getInstance(Context)后，就保存了。
 * 如果发现mAppContext为Null，要看一下调用的方式是否不对。
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/4/17
 */
public class ActivityUtils {
    private static final String TAG = "ActivityManager";
    private static Stack<Activity> activityStack;

    private static ActivityUtils instance;
    /**
     * App在第一次打开时，会注册LifeCycleHandler，打开Activity时，会进行push，同时传入ApplicationContext
     * 保存成静态变量
     */
    private static Context mAppContext;

    private String tag = "";

    /**
     * <单例方法>
     * <功能详细描述>
     *
     * @return 该对象的实例
     * @see [类、类#方法、类#成员]
     */
    public static ActivityUtils getInstance() {
        return getInstance(null);
    }

    public static ActivityUtils getInstance(Context appContext) {
        if (instance == null) {
            instance = new ActivityUtils();
        }
        if (mAppContext == null && appContext != null) {
            mAppContext = appContext;
        }
        return instance;
    }

    /**
     * <获取当前栈顶Activity>
     * <功能详细描述>
     *
     * @see [类、类#方法、类#成员]
     */
    public Activity currentActivity() {
        if (activityStack == null || activityStack.size() == 0) {
            return null;
        }
        Activity activity = activityStack.lastElement();

        L.d(TAG, "get current activity:" + activity.getClass().getSimpleName());
        return activity;
    }

    /**
     * <获取当前栈顶Activity>
     * <功能详细描述>
     *
     * @see [类、类#方法、类#成员]
     */
    public void popCurrentActivity() {
        popActivity(currentActivity());
    }

    /**
     * 讲道理来说，mAppContext不可能为Null
     * @return mAppContext
     */
    public Context getAppContext() {
        return mAppContext;
    }

    /**
     * <将Activity入栈>
     * <功能详细描述>
     *
     * @see [类、类#方法、类#成员]
     */
    public void pushActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        L.d(TAG, "push stack activity:" + activity.getClass().getSimpleName());
        activityStack.add(activity);
    }

    /**
     * <退出栈顶Activity>
     * <功能详细描述>
     *
     * @see [类、类#方法、类#成员]
     */
    public void popActivity(Activity activity) {
        if (activity != null) {
            activity.finish();
            L.d(TAG, "remove current activity:" + activity.getClass().getSimpleName());
            activityStack.remove(activity);
            activity = null;
        }
    }

    /**
     * 将制定activity从栈中移除
     */
    public void removeActivity(Activity activity) {
        if (activity != null) {
            ActivityUtils.activityStack.remove(activity);
        }
    }

    /**
     * <退出栈中所有Activity,当前的activity除外>
     * <功能详细描述>
     *
     * @see [类、类#方法、类#成员]
     */
    public void popAllActivityExceptMain(Class cls) {
        while (true) {
            Activity activity = currentActivity();
            if (activity == null) {
                break;
            }
            if (activity.getClass().equals(cls)) {
                break;
            }

            popActivity(activity);
        }
    }

    public void popAllActivity() {
        while (true) {
            Activity activity = currentActivity();
            if (activity == null) {
                break;
            }
            popActivity(activity);
        }
    }

    public void popAllActivity(String relogin) {
        instance.tag = relogin;
        popAllActivity();
    }

    public String getTag() {
        return tag;
    }

    /**
     * 如果栈顶是这个 class 就finish掉
     */
    public void popActivity(Class<? extends Activity> activityClass) {
        Activity activity = currentActivity();
        if (activity != null && activity.getClass().getName().equals(activityClass.getName())) {
            popActivity(activity);
        }
    }

    public int getSize() {
        if (activityStack != null) {
            return activityStack.size();
        } else {
            return 0;
        }
    }

    public void gotoActivity(Class<? extends Activity> aClass) {
        while (getSize()>1&&currentActivity().getClass() != aClass) {
            popCurrentActivity();
        }
    }

    /**
     * pop指导剩余的数量
     *
     * @param remainSize 栈中剩余的数量
     */
    public void popActivityRemain(int remainSize) {
        while (remainSize < getSize()) {
            popCurrentActivity();
        }
    }
}
