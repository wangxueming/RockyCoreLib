package com.rocky.router;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * 在Application中初始化
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/1/28
 */
public class WFRouter {

    public static void init(Application app, boolean isDebug) {
        if (isDebug) {
            // 打印日志
            ARouter.openLog();
            // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            ARouter.openDebug();
        }
        ARouter.init(app);

        ARouter.getInstance().inject(app);
    }
}
