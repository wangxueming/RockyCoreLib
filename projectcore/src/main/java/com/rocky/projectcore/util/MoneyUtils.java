package com.rocky.projectcore.util;

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

    public static void main(String[] args) {
        System.out.println(fmtMicrometer(123456789.1234));
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
