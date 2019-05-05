package com.rocky.net;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.Log;

/**
 *
 *
 * @author wangxueming
 * @version 1.0.0
 * @date 2018/12/14
 */
public class AppUtils {

    private static Boolean isDebug = null;

    public static boolean isDebug() {
        return isDebug == null ? false : isDebug.booleanValue();
    }

    /**
     * Sync lib debug with app's debug value. Should be called in module Application
     *
     * @param context
     */
    public static void syncIsDebug(Context context) {
        if (isDebug == null) {
            isDebug = context.getApplicationInfo() != null &&
                    (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        }
    }
}
