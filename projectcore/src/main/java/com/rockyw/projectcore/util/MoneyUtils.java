package com.rockyw.projectcore.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author: WangXueMing
 * @version: 1.0.0
 * @date: 2019/1/11
 */
public class MoneyUtils {
    /**
     * 格式化数字为千分位。
     * 保留两位小数
     * 例如输入 123456789012345.12345 输出123,456,789,012,345.12
     *
     * @param money
     * @return String 格式化的
     */
    public static String fmtMicrometer(double money) {
        return new DecimalFormat("###,##0.00").format(money);
    }

    /**
     * 格式化数字为千分位。
     * 例如输入 123456789012345.12345 输出123,456,789,012,345.12
     *
     * @param money
     * @return String 格式化的
     */
    public static String fmtMicrometer1(double money) {
        return new DecimalFormat("###,##0").format(money);
    }

    /**
     * 格式化数字。
     * 保留两位小数
     * 例如输入 123456789012345.12345 输出123,456,789,012,345.12
     * 例如输入 123456789012345.00000 输出123,456,789,012,345
     *
     * @param money
     * @return String 格式化的
     */
    public static String fmtMicrometer2(double money) {
        return new DecimalFormat("####0.00").format(money);
    }

    /**
     * 格式化数字。
     * 保留一位小数
     * 例如输入 123456789012345.12345 输出123,456,789,012,345.12
     * 例如输入 123456789012345.00000 输出123,456,789,012,345
     *
     * @param money
     * @return String 格式化的
     */
    public static String fmtMicrometer3(double money) {
        return new DecimalFormat("####0.0").format(money);
    }

    /**
     * 四舍五入
     *
     * @param origin
     * @param precision
     * @return
     */
    public static double roundHalfDown(double origin, int precision) {
        return new BigDecimal(origin).setScale(precision, BigDecimal.ROUND_HALF_DOWN).doubleValue();
    }

    /**
     * 舍尾！！！
     *
     * @param origin
     * @param precision
     * @return
     */
    public static double roundDown(double origin, int precision) {
        String pattern = "#0";
        if (precision > 0) {
            pattern += ".";
            for (int i = 0; i < precision+2; i++) {
                pattern += "0";
            }
        }
        DecimalFormat df = new DecimalFormat(pattern);
        String result = df.format(origin);
        // 获取小数 . 号第一次出现的位置
        int index = firstIndexOf(result, ".");
        return Double.parseDouble(result.substring(0, index + precision + 1));
    }

    public static int firstIndexOf(String str, String pattern) {
        for (int i = 0; i < (str.length() - pattern.length()); i++) {
            int j = 0;
            while (j < pattern.length()) {
                if (str.charAt(i + j) != pattern.charAt(j)) {
                    break;
                }
                j++;
            }
            if (j == pattern.length()) {
                return i;
            }
        }
        return -1;
    }
    public static void main(String[] args) {
        System.out.println(roundDown(12789.198, 4));
        System.out.println(fmtMicrometer(123456789.0000));
        System.out.println(fmtMicrometer(123456789));
        System.out.println(fmtMicrometer1(123456789.1234));
        System.out.println(fmtMicrometer1(123456789.0000));
        System.out.println(fmtMicrometer1(123456789));
        System.out.println(fmtMicrometer2(123456789.1234));
        System.out.println(fmtMicrometer2(123456789.0000));
        System.out.println(fmtMicrometer2(123456789));
        System.out.println(fmtMicrometer3(123456789.1234));
        System.out.println(fmtMicrometer3(123456789.0000));
        System.out.println(fmtMicrometer3(123456789));
    }
}
