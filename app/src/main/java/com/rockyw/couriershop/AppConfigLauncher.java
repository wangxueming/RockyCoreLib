package com.rockyw.couriershop;

import com.rockyw.projectcore.common.router.CommonServerData;
import com.rockyw.projectcore.net.ServerConfig;
import com.rockyw.projectcore.util.DebugUtil;

/**
 *
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/2/14
 */
public class AppConfigLauncher {

    /**
     * 服务器模式
     */
    public static int SERVER_TYPE;
    static {
        if (!BuildConfig.DEBUG) {
            // release
            SERVER_TYPE = CommonServerData.T_SERVICE_RELEASE;
        } else {
            // debug
            SERVER_TYPE = DebugUtil.getServiceType(WFApplication.getAppContext());
        }
    }

    public static void init() {
        configServer();
    }

    public static void reInit() {
        if (!BuildConfig.DEBUG) {
            // release
            SERVER_TYPE = CommonServerData.T_SERVICE_RELEASE;
        } else {
            // debug
            SERVER_TYPE = DebugUtil.getServiceType(WFApplication.getAppContext());
        }
        configServer();
    }

    /**
     * 接口服务器
     */
    private static void configServer() {
        ServerConfig.setServerType(SERVER_TYPE);
    }
}
