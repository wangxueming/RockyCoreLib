package com.rocky.locate.network;

import android.content.Context;

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
     * @param context
     * @return
     */
    public static ServerApi getServerApi(final Context context) {
        if (sServerApi == null) {
            sServerApi = new Server().createServerApi(context.getApplicationContext(), ServerApi.class);
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
