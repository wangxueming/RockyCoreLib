package com.rocky.projectcore;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rocky.projectcore.common.router.RouterUrl;
import com.rocky.projectcore.login.IAccountService;
import com.rocky.projectcore.publicdata.IPublicDataService;
import com.rocky.projectcore.publicserver.IPublicService;

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

    public static IPublicService getPublicService() {
        return ((IPublicService) ARouter.getInstance().build(RouterUrl.SERVER_PUBLIC_SERVICE).navigation());
    }

}
