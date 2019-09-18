package com.rockyw.projectcore.util.time;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @author: WangXueMing
 * @version: 1.0.0
 * @date: 2019/7/22
 */
public interface TimeConst {
    String YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
    String YYYYMMDD = "yyyy-MM-dd";
    long ONE_DAY_MILLISECOND = 3600000 * 24;
    DateFormat DEFAULT_FORMAT = new SimpleDateFormat(YYYYMMDDHHMMSS);
    DateFormat DEFAULT_SIMPLE_FORMAT = new SimpleDateFormat(YYYYMMDD);
    String YYYYMMDDHH = "yyyy-MM-dd HH";
    String YYYYMMDDHHMM = "yyyy-MM-dd HH:mm";
}
