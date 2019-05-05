package com.rocky.projectcore.common.router;

/**
 * @author: WangXueMing
 * @version: 1.0.0
 * @date: 2019/2/14
 */
public interface CommonServerData {
    String FILE_NAME_SAVE_SERVER = "WF_FinT_Server_Type";

    int T_SERVICE_DEBUG = 1;
    int T_SERVICE_PRESET = 2;
    int T_SERVICE_RELEASE = 3;
    int T_SERVICE_DEFAULT = T_SERVICE_DEBUG;

    String DEBUG_SERVICE_TYPE = "debug_service_type";
}
