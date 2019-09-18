package com.rockyw.core.util;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.text.format.DateUtils;

import com.rockyw.core.util.logger.L;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 在实际项目中，总可能会出现各种各样的时间格式，花样百出
 * 后端给N种格式，前端再转换再显示。
 * 讲道理，是要统一后端给的格式：时间戳。
 * 这个后端给的时间，是要结合时区的。唯有时间戳不可破。
 * 为了应对这种情况，前端必须适配。
 * 这是一个工具类，要具备通用性。
 * 这个类，应该提供各种类型转换成long的方法
 * 提供，long转换成各种格式的方法。（当然，时间格式，除了通用的都是模块自己提供）
 * 做法：
 * 1. 子模块，可以自己定义TimeConst。
 * 2. 子模块先将后端的返回字段，转换成long的时间戳
 * 2. 根据 子模块自定义的格式，转换成需要的格式。
 * <p>
 * 这个类包括：
 * 1、getTime从String、Date、Time获取时间戳
 * 2、从long时间戳，转化为各种格式
 * 3、String转Date
 * 4、String转Calendar
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/7/22
 */
public class TimeUtils {

    protected TimeUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }


    /**
     * Formatted time string to the milliseconds.
     *
     * @param time       The formatted time string.
     * @param timeFormat The format.
     * @return the milliseconds
     */
    public static long getTime(final String time, @NonNull final String timeFormat) {
        return getTime(time, new SimpleDateFormat(timeFormat));
    }

    /**
     * Formatted time string to the milliseconds.
     *
     * @param time   The formatted time string.
     * @param format The format.
     * @return the milliseconds
     */
    public static long getTime(final String time, @NonNull final DateFormat format) {
        try {
            return format.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 从Date转成时间戳
     *
     * @param date
     * @return
     */
    public static long getTime(@NonNull final Date date) {
        return date.getTime();
    }

    /**
     * 从Date转成时间戳
     *
     * @param date
     * @return
     */
    public static long getTime(@NonNull final Calendar date) {
        return getTime(date.getTime());
    }

    /**
     * Milliseconds to the formatted time string.
     *
     * @param millis     The milliseconds.
     * @param timeFormat The format.
     * @return the formatted time string
     */
    public static String millis2String(final long millis, @NonNull final String timeFormat) {
        return millis2String(millis, new SimpleDateFormat(timeFormat));
    }

    /**
     * UTC time to format
     *
     * @param utcTime
     * @param format  yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String formatTo(String utcTime, String format) {
        if (utcTime == null) {
            return "";
        }
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSZ");
        Date result;
        try {
            result = df.parse(utcTime);
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            return sdf.format(result);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return utcTime;
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
     * Formatted time string to the milliseconds.
     *
     * @param time   The formatted time string.
     * @param timeFormat The format.
     * @return the milliseconds
     */
    public static long string2Millis(final String time, @NonNull final String timeFormat) {
        return string2Millis(time, new SimpleDateFormat(timeFormat));
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
     * Formatted time string to the date.
     *
     * @param time       The formatted time string.
     * @param timeFormat The format.
     * @return the date
     */
    public static Date string2Date(final String time, @NonNull final String timeFormat) {
        return string2Date(time, new SimpleDateFormat(timeFormat));
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
     * Formatted time string to the date.
     *
     * @param time       The formatted time string.
     * @param timeFormat The format.
     * @return the date
     */
    public static Calendar string2Calendar(final String time, @NonNull final String timeFormat) {
        return string2Calendar(time, new SimpleDateFormat(timeFormat));
    }

    /**
     * Formatted time string to the date.
     *
     * @param time   The formatted time string.
     * @param format The format.
     * @return the date
     */
    public static Calendar string2Calendar(final String time, @NonNull final DateFormat format) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(format.parse(time));
            return calendar;
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
        System.out.println(formatLongTime(173234732L - 86400000 * 2));
    }


}
