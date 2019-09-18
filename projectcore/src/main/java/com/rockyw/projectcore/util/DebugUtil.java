package com.rockyw.projectcore.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.rockyw.projectcore.common.router.CommonServerData;

/**
 * 获取当前系统调试状态
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/4/4
 */
public class DebugUtil {

    public static void setServiceType(Context context, int type) {
        SharedPreferences pm = context.getSharedPreferences(CommonServerData.FILE_NAME_SAVE_SERVER, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = pm.edit();
        edit.putInt(CommonServerData.DEBUG_SERVICE_TYPE, type);
        edit.apply();
    }

    public static int getServiceType(Context context) {
        SharedPreferences pm = context.getSharedPreferences(CommonServerData.FILE_NAME_SAVE_SERVER, Context.MODE_PRIVATE);
        return pm.getInt(CommonServerData.DEBUG_SERVICE_TYPE, CommonServerData.T_SERVICE_DEFAULT);
    }
}
