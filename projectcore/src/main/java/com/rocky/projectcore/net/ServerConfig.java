package com.rocky.projectcore.net;

import com.rocky.projectcore.common.router.CommonServerData;

/**
 *
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/2/14
 */
public class ServerConfig {

    static final String DEV_SERVER_URL = "https://dev.yuming.com";
    static final String PRESET_SERVER_URL = "https://preset.yuming.com";
    static final String RELEASE_SERVER_URL = "https://relese.yuming.com";

    /**
     * 更改默认服务器时，不需要改这个字段，修改{@link com.rocky.weather.AppConfig}内的 SERVER_TYPE 字段即可
     */
    private static int SERVER_TYPE;

    public static void setServerType(int type) {
        SERVER_TYPE = type;
    }

    public static String getServer() {
        if (SERVER_TYPE == CommonServerData.T_SERVICE_DEBUG) {
            return DEV_SERVER_URL;
        } else if (SERVER_TYPE == CommonServerData.T_SERVICE_PRESET) {
            return PRESET_SERVER_URL;
        } else if (SERVER_TYPE == CommonServerData.T_SERVICE_RELEASE) {
            return RELEASE_SERVER_URL;
        } else {
            return DEV_SERVER_URL;
        }
    }
}
