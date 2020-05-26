package com.rockyw.couriershop;

import com.rockyw.core.BaseApplication;
import com.rockyw.core.LifecycleHandler;
import com.rockyw.core.base.BaseEventBusBean;
import com.rockyw.core.util.DimensionUtil;
import com.rockyw.couriershop.data.DaoMaster;
import com.rockyw.couriershop.data.DaoSession;
import com.rockyw.couriershop.data.MyDataBaseHelper;
import com.rockyw.router.WFRouter;
import com.umeng.commonsdk.UMConfigure;

import org.greenrobot.greendao.database.Database;

/**
 * 壳工程的Application
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2018/12/28
 */
public class WFApplication extends BaseApplication {

    private static WFApplication mWFApplication;

    public static WFApplication getInstance() {
        return mWFApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mWFApplication = this;

        WFRouter.init(this, BuildConfig.DEBUG);

        AppConfigLauncher.init();

        //#个推的初始化 没用到之前先屏蔽
        // PushHelper.initPushService(this);

        registerActivityLifecycleCallbacks(new LifecycleHandler());

        //#配置umeng
        configUMeng();

        DimensionUtil.init(this);
//        initDatabase();
    }

    @Override
    protected void onEvent(BaseEventBusBean event) {
        super.onEvent(event);
    }

    private void configUMeng() {
        /**
         * 初始化common库
         * 参数1:上下文，不能为空
         * 参数2:设备类型，UMConfigure.DEVICE_TYPE_PHONE为手机、UMConfigure.DEVICE_TYPE_BOX为盒子，默认为手机
         * 参数3:Push推送业务的secret
         */
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "");
    }

    private void initDatabase() {
        // DevOpenHelper 在数据库升级时会删除所有的表，release 版本要记得换回来
        MyDataBaseHelper helper = new MyDataBaseHelper(this, "stock_rocky.db");
        Database database = helper.getWritableDb();
        mDaoSession = new DaoMaster(database).newSession();
    }

    private DaoSession mDaoSession;

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

}
