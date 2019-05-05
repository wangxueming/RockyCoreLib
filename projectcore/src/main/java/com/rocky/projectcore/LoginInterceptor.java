package com.rocky.projectcore;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.rocky.core.util.logger.L;
import com.rocky.projectcore.common.router.RouterUrl;
import com.rocky.projectcore.login.IAccountService;

/**
 * 登陆拦截器
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/1/24
 */
@Interceptor(name = RouterUrl.SERVER_LOGIN, priority = 6)
public class LoginInterceptor implements IInterceptor {

    private Context mContext;
    private boolean clearTokenOneShot = true;

    @Override
    public void init(Context context) {
        mContext = context;
        L.i("LoginInterceptor 初始化");
        if (clearTokenOneShot) {
            IAccountService accountService = UrlServiceManager.getAccountService();
            accountService.clearUserInfoExcludeSystemSettings(mContext);
            clearTokenOneShot = false;
        }
    }

    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        final IAccountService accountService = UrlServiceManager.getAccountService();
        if (accountService.isLogin(mContext)) {
            callback.onContinue(postcard);
        } else {
            accountService.startLogin(mContext);
            callback.onInterrupt(null);
        }
    }
}
