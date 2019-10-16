package com.rockyw.projectcore.net;

import android.text.TextUtils;

import com.rockyw.projectcore.common.router.CommonServerData;


/**
 *
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/2/14
 */
public class ServerConfig {

    private static final String FLAVOR_ENV1 = "env1";
    private static final String FLAVOR_ENV2 = "env2";

    static final String DEV_SERVER_URL = "https://www.wjtest.local";
    static final String DEV2_SERVER_URL = "https://www.wjtest2.local";
    static final String PRESET_SERVER_URL = "https://test.winfae.com";
    static final String RELEASE_SERVER_URL = "https://www.winfae.com";

    static final String H5_DEV_SERVER_URL = "https://h5.wjtest.local";
    static final String H5_DEV2_SERVER_URL = "https://h5.wjtest2.local";
    static final String H5_PRESET_SERVER_URL = "https://h5test.winfae.com";
    static final String H5_RELEASE_SERVER_URL = "https://h5.winfae.com";
    /**
     * 更改默认服务器时，不需要改这个字段，修改{@link com.rockyw.fintransation.AppConfigLauncher}内的 SERVER_TYPE 字段即可
     */
    private static int SERVER_TYPE;
    private static String SERVER_FLAVOR;

    public static void setServerType(int type) {
        SERVER_TYPE = type;
    }

    public static void setServerFlavor(String flavor) {
        SERVER_FLAVOR = flavor;
    }

    public static String getServer() {
        if (SERVER_TYPE == CommonServerData.T_SERVICE_DEBUG) {
            return TextUtils.equals(SERVER_FLAVOR, FLAVOR_ENV1) ? DEV_SERVER_URL : DEV2_SERVER_URL;
        } else if (SERVER_TYPE == CommonServerData.T_SERVICE_PRESET) {
            return PRESET_SERVER_URL;
        } else if (SERVER_TYPE == CommonServerData.T_SERVICE_RELEASE) {
            return RELEASE_SERVER_URL;
        } else {
            return DEV_SERVER_URL;
        }
    }

    public static String getH5Server() {
        if (SERVER_TYPE == CommonServerData.T_SERVICE_DEBUG) {
            return TextUtils.equals(SERVER_FLAVOR, FLAVOR_ENV1) ? H5_DEV_SERVER_URL : H5_DEV2_SERVER_URL;
        } else if (SERVER_TYPE == CommonServerData.T_SERVICE_PRESET) {
            return H5_PRESET_SERVER_URL;
        } else if (SERVER_TYPE == CommonServerData.T_SERVICE_RELEASE) {
            return H5_RELEASE_SERVER_URL;
        } else {
            return H5_DEV_SERVER_URL;
        }
    }

    public static boolean isEnv1() {
        return TextUtils.equals(SERVER_FLAVOR, FLAVOR_ENV1);
    }
}
