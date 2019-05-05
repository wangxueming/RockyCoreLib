package com.rocky.core.util.logger;

import com.orhanobut.logger.Logger;

/**
 * 这是一份漂亮版本的Logger，特别适合打印网络日志
 * @author wangxueming
 * @date 2018/12/13 下午2:43
 */
public class PrettyL {

    private PrettyL() {
        throw new UnsupportedOperationException("cannot be instantiated！");
    }

    public static void d(String message) {
        Logger.d(message);
    }

    public static void i(String message) {
        Logger.i(message);
    }

    public static void w(String message) {
        Logger.w(message);
    }

    public static void w(String message, Throwable e) {
        Logger.w(message + "：" + (e != null ? e.toString() : "no_throwable"));
    }

    public static void e(String message, Throwable e) {
        Logger.e(e, message);
    }

    public static void json(String json) {
        Logger.json(json);
    }
}
