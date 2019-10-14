package com.rockyw.projectcore.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.winfae.projectcore.common.router.WfCommonServerData;

/**
 * 获取当前系统调试状态
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/4/4
 */
public class DebugUtil {

    public static void setServiceType(Context context, int type) {
        SharedPreferences pm = context.getSharedPreferences(WfCommonServerData.FILE_NAME_SAVE_SERVER, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = pm.edit();
        edit.putInt(WfCommonServerData.DEBUG_SERVICE_TYPE, type);
        edit.apply();
    }

    public static int getServiceType(Context context) {
        SharedPreferences pm = context.getSharedPreferences(WfCommonServerData.FILE_NAME_SAVE_SERVER, Context.MODE_PRIVATE);
        return pm.getInt(WfCommonServerData.DEBUG_SERVICE_TYPE, WfCommonServerData.T_SERVICE_DEFAULT);
    }
}
