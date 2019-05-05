package com.rocky.core.util.logger;

import android.util.Log;

import com.rocky.core.BaseApplication;

/**
 * 这是通用的日志打印工具，不够漂亮。打印网络日志用@see PrettyL
 * @author wangxueming
 * @date 2018/12/13 下午2:40
 */
public class L {
    private static final String TAG = "WINFAE";

    private L() {
        throw new UnsupportedOperationException("cannot be instantiated！");
    }

    public static void i(Object msg) {
        if (BaseApplication.IS_DEBUG) {
            Log.i(TAG, msg + "");
        }
    }

    public static void d(Object msg) {
        if (BaseApplication.IS_DEBUG) {
            Log.d(TAG, msg + "");
        }
    }


    public static void w(Object msg) {
        if (BaseApplication.IS_DEBUG) {
            Log.w(TAG, msg + "");
        }
    }

    public static void e(Object msg) {
        if (BaseApplication.IS_DEBUG) {
            Log.e(TAG, msg + "");
        }
    }

    public static void v(Object msg) {
        if (BaseApplication.IS_DEBUG) {
            Log.v(TAG, msg + "");
        }
    }

    /**
     * 下面是传入自定义tag的函数
     * @param tag
     * @param msg
     */
    public static void i(Object tag, Object msg) {
        if (BaseApplication.IS_DEBUG) {
            Log.i(tag + "", msg + "");
        }
    }

    public static void d(Object tag, Object msg) {
        if (BaseApplication.IS_DEBUG) {
            Log.i(tag + "", msg + "");
        }
    }

    public static void e(Object tag, Object msg) {
        if (BaseApplication.IS_DEBUG) {
            Log.i(tag + "", msg + "");
        }
    }

    public static void v(Object tag, Object msg) {
        if (BaseApplication.IS_DEBUG) {
            Log.i(tag + "", msg + "");
        }
    }

    public static void logE(String content) {
        if (BaseApplication.IS_DEBUG) {
            int p = 20480;
            long length = content.length();
            if (length < p || length == p) {
                Log.e(TAG, content);
            } else {
                while (content.length() > p) {
                    String logContent = content.substring(0, p);
                    content = content.replace(logContent, "");
                    Log.e(TAG, logContent);
                }
                Log.e(TAG + "", content);
            }
        }
    }

    /**
     * 默认每次缩进两个空格
     */
    private static final String empty = "  ";

    public static String format(String json) {
        if (!BaseApplication.IS_DEBUG) {
            return "";
        }
        try {
            int empty = 0;
            char[] chs = json.toCharArray();
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < chs.length; ) {
                //若是双引号，则为字符串，下面if语句会处理该字符串
                if (chs[i] == '\"') {
                    stringBuilder.append(chs[i]);
                    i++;
                    //查找字符串结束位置
                    for (; i < chs.length; ) {
                        //如果当前字符是双引号，且前面有连续的偶数个\，说明字符串结束
                        if (chs[i] == '\"' && isDoubleSerialBackslash(chs, i - 1)) {
                            stringBuilder.append(chs[i]);
                            i++;
                            break;
                        } else {
                            stringBuilder.append(chs[i]);
                            i++;
                        }

                    }
                } else if (chs[i] == ',') {
                    stringBuilder.append(',').append('\n').append(getEmpty(empty));

                    i++;
                } else if (chs[i] == '{' || chs[i] == '[') {
                    empty++;
                    stringBuilder.append(chs[i]).append('\n').append(getEmpty(empty));

                    i++;
                } else if (chs[i] == '}' || chs[i] == ']') {
                    empty--;
                    stringBuilder.append('\n').append(getEmpty(empty)).append(chs[i]);

                    i++;
                } else {
                    stringBuilder.append(chs[i]);
                    i++;
                }
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return json;
        }

    }

    private static boolean isDoubleSerialBackslash(char[] chs, int i) {
        int count = 0;
        for (int j = i; j > -1; j--) {
            if (chs[j] == '\\') {
                count++;
            } else {
                return count % 2 == 0;
            }
        }

        return count % 2 == 0;
    }

    /**
     * 缩进
     *
     * @param count
     * @return
     */
    private static String getEmpty(int count) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < count; i++) {
            stringBuilder.append(empty);
        }

        return stringBuilder.toString();
    }

}
