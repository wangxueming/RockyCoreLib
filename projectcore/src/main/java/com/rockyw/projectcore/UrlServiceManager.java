package com.rockyw.projectcore;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rockyw.projectcore.common.router.RouterUrl;
import com.rockyw.projectcore.service.buriedpoint.IBuriedPointService;
import com.rockyw.projectcore.service.login.IAccountService;
import com.rockyw.projectcore.service.publicdata.IPublicDataService;
import com.rockyw.projectcore.service.download.IDownloadService;

/**
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/1/3
 */
public class UrlServiceManager {

    public static IAccountService getAccountService() {
        return ((IAccountService) ARouter.getInstance().build(RouterUrl.SERVER_ACCOUNT).navigation());
    }

    public static IPublicDataService getPublicDataService() {
        return ((IPublicDataService) ARouter.getInstance().build(RouterUrl.SERVER_PUBLIC_DATA).navigation());
    }

    public static IDownloadService getPublicService() {
        return ((IDownloadService) ARouter.getInstance().build(RouterUrl.SERVER_PUBLIC_SERVICE).navigation());
    }

    public static IBuriedPointService getBuriedPoint() {
        return ((IBuriedPointService) ARouter.getInstance().build(RouterUrl.SERVER_BURIED_POINT).navigation());
    }

}
