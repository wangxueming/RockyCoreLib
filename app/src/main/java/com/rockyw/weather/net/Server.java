package com.rocky.weather.net;

import android.content.Context;

import com.rocky.core.BaseApplication;
import com.rocky.core.util.AppUtils;
import com.rocky.net.BaseServer;
import com.rocky.projectcore.net.ServerConfig;

import java.util.HashMap;

/**
 * @author wangxueming
 * @date 2018/3/20
 */
public class Server extends BaseServer {

    private static ServerApi sServerApi;

    /**
     * 服务器接口对象
     *
     * @return
     */
    public static ServerApi getServerApi() {
        return getServerApi(BaseApplication.getAppContext());
    }

    /**
     * 服务器接口对象
     *
     * @param context
     * @return
     */
    public static ServerApi getServerApi(final Context context) {
        if (sServerApi == null) {
            sServerApi = new Server().createServerApi(context == null ? null : context.getApplicationContext(), ServerApi.class);
        }
        return sServerApi;
    }

    /**
     * 重置Api,当连接Header内容需要改变时候，必须调用reset方法
     */
    public static void resetServerApi() {
        sServerApi = null;
    }

    @Override
    public String getInternalServer() {
        return getServer();
    }

    /**
     * 设置Http Head
     *
     * @return
     */
    @Override
    public HashMap<String, String> getRequestHead() {
        HashMap<String, String> headMap = new HashMap<>(20);
        if (sContext != null) {
            headMap.put(HEAD_KEY_APP_VERSION, Integer.toString(AppUtils.getVerCode(sContext)));
            headMap.put(HEAD_KEY_UG, "android");
        }
        return headMap;
    }

    /**
     * 获取服务器地址
     *
     * @return
     */
    public static String getServer() {
        return ServerConfig.getServer();
    }
}
