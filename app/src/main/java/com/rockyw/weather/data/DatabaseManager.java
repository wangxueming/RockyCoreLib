package com.rockyw.weather.data;

import android.os.HandlerThread;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Author:      hanjie
 * Created at:  2017/10/10.
 * Email:
 * Description:
 */


public class DatabaseManager {

    private static HandlerThread sThread;

    static {
        sThread = new HandlerThread("data_base");
        sThread.start();
    }

    public static Scheduler getScheduler() {
        return AndroidSchedulers.from(sThread.getLooper());
    }

    /**
     * 删除当前用户所有数据库缓存
     */
    public static void clearCurrentUserCache() {
//        UnreadCountCacheManager.deleteAll();
    }

    /**
     * 删除所有用户数据库缓存
     */
    public static void clear() {
//        UnreadCountCacheManager.clear();
    }

}
