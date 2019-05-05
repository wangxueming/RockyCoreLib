package com.rocky.projectcore.login;

/**
 * 自定义的UriResultCode，为了避免冲突，自定义的建议用负数值
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/1/3
 */
public interface CustomUriResult {

    int CODE_LOGIN_CANCEL = -100;
    int CODE_LOGIN_FAILURE = -101;

    int CODE_LOCATION_FAILURE = -200;
}
