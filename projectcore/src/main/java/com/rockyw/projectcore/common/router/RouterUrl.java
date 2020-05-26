package com.rockyw.projectcore.common.router;

/**
 * 所有的页面数据都在这里
 *
 * @author wangxueming
 * @version 1.0.0
 * @date 2018/12/20
 */
public interface RouterUrl {
    /**
     * ---------------------登录注册---------------------
     */
    /**
     * 登录页
     */
    String LOGIN_MAIN = "/login/main";

    /**
     * 注册页
     */
    String LOGIN_REGISTER = "/login/register";

    /**
     * ---------------------起始页---------------------
     */
    String SPLASH_PAGE = "/main/splash";
    /**
     * ---------------------主页---------------------
     */
    String HOME = "/home/main";
    String CITY_SEARCH = "/home/city/search";
    String CITY_MANAGE = "/home/city/manage";

    /**
     * 其他模块
     */
    String OTHERS_SHOW_H5 = "/other/show_h5";
    String OTHERS_JUMP_WEB = "/other/jump_web";
    String OTHERS_DOWNLOAD_APK = "/other/download_apk";
    String OTHERS_JUMP_INSTALL = "/other/jump_install";

    /**
     * service
     */
    String SERVER_CITIES = "/server/cities";
    String SERVER_DB = "/server/db";

    /**
     * INTERCEPTOR
     */
    String INTERCEPTOR_CITIES = "/server/cities";
}
