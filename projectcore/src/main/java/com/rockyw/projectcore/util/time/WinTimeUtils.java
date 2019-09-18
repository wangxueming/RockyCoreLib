package com.rockyw.projectcore.util.time;

import com.rockyw.core.util.TimeUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * @author: WangXueMing
 * @version: 1.0.0
 * @date: 2019/7/22
 */
public class WinTimeUtils extends TimeUtils implements TimeConst {

    public WinTimeUtils() {
        super();
    }

    /**
     * 将时间字符串转为时间戳
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
     *
     * @param time The formatted time string.
     * @return the milliseconds
     */
    public static long getTime(final String time) {
        return getTime(time, DEFAULT_FORMAT);
    }

    /**
     * 将时间戳转为时间字符串
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
     *
     * @param millis The milliseconds.
     * @return the formatted time string
     */
    public static String millis2String(final long millis) {
        return millis2String(millis, DEFAULT_FORMAT);
    }

    /**
     * 转换成UTC时间格式
     *
     * @param time
     * @return
     */
    public static String transferTimeUtc(String time) {
        return millis2String(getTime(time), DEFAULT_SIMPLE_FORMAT);
    }

    /**
     * 将时间字符串转为 Date 类型
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
     *
     * @param time The formatted time string.
     * @return the date
     */
    public static Date string2Date(final String time) {
        return string2Date(time, DEFAULT_FORMAT);
    }

    /**
     * 将时间字符串转为 Calendar 类型
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
     *
     * @param time The formatted time string.
     * @return the date
     */
    public static Calendar string2Calendar(final String time) {
        return string2Calendar(time, DEFAULT_FORMAT);
    }


    /**
     * 将时间字符串转为时间戳
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
     *
     * @param time The formatted time string.
     * @return the milliseconds
     */
    public static long string2Millis(final String time) {
        return string2Millis(time, DEFAULT_FORMAT);
    }
}
