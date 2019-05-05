package com.rocky.core.util;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.text.format.DateUtils;

import com.rocky.core.util.logger.L;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author by TOME .
 * @data on      2018/6/25 14:44
 * @describe ${时间相关}
 */

public class TimeUtils {

    public static final long ONE_DAY_MILLISECOND = 3600000 * 24;

    @SuppressLint("SimpleDateFormat")
    private static final DateFormat DEFAULT_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @SuppressLint("SimpleDateFormat")
    private static final DateFormat DEFAULT_SIMPLE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    @SuppressLint("SimpleDateFormat")
    private static final DateFormat DEFAULT_DURATION_FORMAT = new SimpleDateFormat("d天H小时mm分ss秒");
    @SuppressLint("SimpleDateFormat")
    private static final DateFormat DEFAULT_DURATION_FORMAT2 = new SimpleDateFormat("H小时mm分ss秒");

    private TimeUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 获取时间戳
     *
     * @return
     */
    public static String getCurString() {
        long msg = System.currentTimeMillis();

        return Long.toString(msg);
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

    public static String millis2StringCustom(final long millis) {
        if (millis < ONE_DAY_MILLISECOND) {
            return millis2String(millis, DEFAULT_DURATION_FORMAT2);
        } else {
            return millis2String(millis, DEFAULT_DURATION_FORMAT);
        }
    }

    public static String transferTimeUtc(String time) {
        return millis2String(string2Millis(time), DEFAULT_SIMPLE_FORMAT);
    }

    /**
     * Milliseconds to the formatted time string.
     *
     * @param millis The milliseconds.
     * @param format The format.
     * @return the formatted time string
     */
    public static String millis2String(final long millis, @NonNull final DateFormat format) {
        return format.format(new Date(millis));
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

    /**
     * Formatted time string to the milliseconds.
     *
     * @param time   The formatted time string.
     * @param format The format.
     * @return the milliseconds
     */
    public static long string2Millis(final String time, @NonNull final DateFormat format) {
        try {
            return format.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
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
     * Formatted time string to the date.
     *
     * @param time   The formatted time string.
     * @param format The format.
     * @return the date
     */
    public static Date string2Date(final String time, @NonNull final DateFormat format) {
        try {
            return format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取合适型两个时间差
     *
     * @param millis0   毫秒时间戳1
     * @param millis1   毫秒时间戳2
     * @param precision 精度
     *                  <p>precision = 0，返回null</p>
     *                  <p>precision = 1，返回天</p>
     *                  <p>precision = 2，返回天和小时</p>
     *                  <p>precision = 3，返回天、小时和分钟</p>
     *                  <p>precision = 4，返回天、小时、分钟和秒</p>
     *                  <p>precision &gt;= 5，返回天、小时、分钟、秒和毫秒</p>
     * @return 合适型两个时间差
     */
    public static int getFitTimeSpanV2(long millis0, long millis1, int precision) {
        return millis2FitTimeSpanV2(Math.abs(millis0 - millis1), precision);
    }

    @SuppressLint("DefaultLocale")
    public static int millis2FitTimeSpanV2(long millis, int precision) {
        if (millis <= 0) {
            return 0;
        }
        int[] unitLen = {86400000, 3600000, 60000, 1000, 1};
        long mode = 0;
        precision = Math.min(precision, 5);
        mode = millis / unitLen[precision];
        return (int) mode;
    }

    /**
     * 返回今天、昨天、2019-10-10 三种格式
     *
     * @param date
     * @return
     */
    public static String isNow(String date) {
        if (ObjectUtils.isEmpty(date)) {
            return "";
        }

        String dateF = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(dateF);
        String nowDay = "";
        try {
            nowDay = sdf.format(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //今天
        Calendar cal = Calendar.getInstance();
        String yday = sdf.format(cal.getTime());
        if (yday.equals(nowDay)) {
            return "今天";
        }

        //昨天
        cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        yday = sdf.format(cal.getTime());
        if (yday.equals(nowDay)) {
            return "昨天";
        }
        L.i("nowDay：" + nowDay);
        return nowDay;
    }

    /**
     * 倒计时专用
     *
     * @param timeMillis
     * @return
     */
    public static String formatLongTime(long timeMillis) {
        long timeLeftMillis = timeMillis;

        long days = timeLeftMillis / DateUtils.DAY_IN_MILLIS;
        timeLeftMillis = timeLeftMillis % DateUtils.DAY_IN_MILLIS;

        long hours = timeLeftMillis / DateUtils.HOUR_IN_MILLIS;
        timeLeftMillis = timeLeftMillis % DateUtils.HOUR_IN_MILLIS;

        long minutes = timeLeftMillis / DateUtils.MINUTE_IN_MILLIS;
        timeLeftMillis = timeLeftMillis % DateUtils.MINUTE_IN_MILLIS;

        long seconds = timeLeftMillis / DateUtils.SECOND_IN_MILLIS;

        if (days == 0) {
            return String.format("%1$s小时%2$s分%3$s秒", hours, minutes, seconds);
        } else {
            return String.format("%1$s天%2$s小时%3$s分%4$s秒", days, hours, minutes, seconds);
        }
    }

    public static void main(String[] args) {
        System.out.println(formatLongTime(173234732L-86400000*2));
    }


}
