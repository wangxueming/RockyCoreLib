package com.rockyw.core.util;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.StringRes;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.rockyw.core.BaseApplication;
import com.rockyw.core.R;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Toast用这个可靠
 *
 * @author wangxueming
 * @date 2018/12/13 下午2:47
 */
public class Tip {

    private static Object iNotificationManagerObj;

    public enum Type {
        /**
         * 通知
         */
        NOTICE,
        /**
         * 成功
         */
        SUCCESS,
        /**
         * 普通样式
         */
        COMMON
    }

    private static volatile Tip sTip;
    private Context mContext;
    private TextView mTextView;
    private Toast mToast;
    private String mLastTxt;
    private long mLastShowTime;

    private Tip(Context context) {
        this.mContext = context;

        mTextView = new TextView(mContext);
        mTextView.setCompoundDrawablePadding((int) DimensionUtil.dp(9));
        mTextView.setTextColor(Color.WHITE);
        mTextView.setTextSize(12);
        mTextView.setGravity(Gravity.CENTER);
        mTextView.setMinWidth((int) DimensionUtil.dp(200));
        int paddingLeftAndRight = (int) DimensionUtil.dp(23);
        int paddingTopAndBottom = (int) DimensionUtil.dp(9);
        mTextView.setPadding(paddingLeftAndRight, paddingTopAndBottom, paddingLeftAndRight, paddingTopAndBottom);
        ViewCompat.setBackground(mTextView, ContextCompat.getDrawable(mContext, R.drawable.base_bg_tip_toast_success));
        mToast = new Toast(mContext);
        mToast.setView(mTextView);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.setGravity(Gravity.TOP, 0, (int) DimensionUtil.dp(70));
    }

    /**
     * 实例化入口
     *
     * @param context
     * @return
     */
    private static Tip with(Context context) {
        if (sTip == null) {
            synchronized (Tip.class) {
                if (sTip == null) {
                    sTip = new Tip(context.getApplicationContext());
                }
            }
        }
        return sTip;
    }

    public static void on(Type type, @StringRes int resId) {
        switch (type) {
            case NOTICE:
                onNotice(resId);
                break;
            case SUCCESS:
                onSuccess(resId);
                break;
            case COMMON:
                onCommonNotice(resId);
                break;
            default:
                break;
        }
    }

    public static void on(Type type, String tip) {
        switch (type) {
            case NOTICE:
                onNotice(tip);
                break;
            case SUCCESS:
                onSuccess(tip);
                break;
            case COMMON:
                onCommonNotice(tip);
                break;
            default:
                break;
        }
    }

    public static void onLong(Type type, @StringRes int resId) {
        switch (type) {
            case NOTICE:
                onNoticeLong(resId);
                break;
            case SUCCESS:
                onSuccessLong(resId);
                break;
            case COMMON:
                onCommonNoticeLong(resId);
                break;
            default:
                break;
        }
    }

    public static void onLong(Type type, String tip) {
        switch (type) {
            case NOTICE:
                onNoticeLong(tip);
                break;
            case SUCCESS:
                onSuccessLong(tip);
                break;
            case COMMON:
                onCommonNoticeLong(tip);
                break;
            default:
                break;
        }
    }

    public static void onNotice(@StringRes int resId) {
        with(BaseApplication.getAppContext()).notice(resId, Toast.LENGTH_SHORT);
    }

    public static void onNotice(String tip) {
        with(BaseApplication.getAppContext()).notice(tip, Toast.LENGTH_SHORT);
    }

    public static void onNoticeLong(@StringRes int resId) {
        with(BaseApplication.getAppContext()).notice(resId, Toast.LENGTH_LONG);
    }

    public static void onNoticeLong(String tip) {
        with(BaseApplication.getAppContext()).notice(tip, Toast.LENGTH_LONG);
    }

    public static void onSuccess(@StringRes int tipResId) {
        with(BaseApplication.getAppContext()).success(tipResId, Toast.LENGTH_SHORT);
    }

    public static void onSuccess(String tip) {
        with(BaseApplication.getAppContext()).success(tip, Toast.LENGTH_SHORT);
    }

    public static void onSuccessLong(@StringRes int tipResId) {
        with(BaseApplication.getAppContext()).success(tipResId, Toast.LENGTH_LONG);
    }

    public static void onSuccessLong(String tip) {
        with(BaseApplication.getAppContext()).success(tip, Toast.LENGTH_LONG);
    }

    public static void onCommonNotice(@StringRes int tipResId) {
        with(BaseApplication.getAppContext()).commonNotice(tipResId, Toast.LENGTH_SHORT);
    }

    public static void onCommonNotice(String tip) {
        with(BaseApplication.getAppContext()).commonNotice(tip, Toast.LENGTH_SHORT);
    }

    public static void onCommonNoticeLong(@StringRes int tipResId) {
        with(BaseApplication.getAppContext()).commonNotice(tipResId, Toast.LENGTH_LONG);
    }

    public static void onCommonNoticeLong(String tip) {
        with(BaseApplication.getAppContext()).commonNotice(tip, Toast.LENGTH_LONG);
    }

    // ----------------------------------------------------------------------

    private void notice(@StringRes int resId, int duration) {
        notice(mContext.getString(resId), duration);
    }

    private void notice(String s, int duration) {
        show(s, Type.NOTICE, duration);
    }

    private void success(@StringRes int resId, int duration) {
        success(mContext.getString(resId), duration);
    }

    private void success(String s, int duration) {
        //#在源头干掉绿色的框。是经过mingyuan，以及pengfei确认
//        show(s, Type.SUCCESS, duration);
    }

    private void commonNotice(@StringRes int resId, int duration) {
        commonNotice(mContext.getString(resId), duration);
    }

    private void commonNotice(String s, int duration) {
        show(s, Type.COMMON, duration);
    }

    private void show(String s, Type type, int duration) {
        if (TextUtils.equals(mLastTxt, s)) {
            long del = System.currentTimeMillis() - mLastShowTime;
            boolean needShow;
            if (duration == Toast.LENGTH_SHORT) {
                needShow = del > 2000;
            } else if (duration == Toast.LENGTH_LONG){
                needShow = del > 3500;
            } else {
                needShow = del > duration;
            }
            if (!needShow) {
                return;
            }
        } else {
            mLastTxt = s;
        }
        mTextView.setText(s);
        switch (type) {
            case NOTICE:
                ViewCompat.setBackground(mTextView, ContextCompat.getDrawable(mContext, R.drawable.base_bg_tip_toast_notice));
                break;
            case SUCCESS:
                ViewCompat.setBackground(mTextView, ContextCompat.getDrawable(mContext, R.drawable.base_bg_tip_toast_success));
                break;
            case COMMON:
                ViewCompat.setBackground(mTextView, ContextCompat.getDrawable(mContext, R.drawable.base_bg_tip_toast_common_notice));
                break;
            default:
                break;
        }
        mToast.setDuration(duration);
        if (isNotificationEnabled(mContext)) {
            mToast.show();
        } else {
            showSystemToast(mToast);
        }
        mLastShowTime = System.currentTimeMillis();
    }

    /**
     * 显示系统Toast
     */
    private static void showSystemToast(Toast toast) {
        try {
            Method getServiceMethod = Toast.class.getDeclaredMethod("getService");
            getServiceMethod.setAccessible(true);
            //hook INotificationManager
            if (iNotificationManagerObj == null) {
                iNotificationManagerObj = getServiceMethod.invoke(null);

                Class iNotificationManagerCls = Class.forName("android.app.INotificationManager");
                Object iNotificationManagerProxy = Proxy.newProxyInstance(toast.getClass().getClassLoader(), new Class[]{iNotificationManagerCls}, new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        //强制使用系统Toast
                        if ("enqueueToast".equals(method.getName())
                                || "enqueueToastEx".equals(method.getName())) {
                            //华为p20 pro上为enqueueToastEx
                            args[0] = "android";
                        }
                        return method.invoke(iNotificationManagerObj, args);
                    }
                });
                Field sServiceFiled = Toast.class.getDeclaredField("sService");
                sServiceFiled.setAccessible(true);
                sServiceFiled.set(null, iNotificationManagerProxy);
            }
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 消息通知是否开启
     *
     * @return
     */
    private static boolean isNotificationEnabled(Context context) {
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        boolean areNotificationsEnabled = notificationManagerCompat.areNotificationsEnabled();
        return areNotificationsEnabled;
    }
}

