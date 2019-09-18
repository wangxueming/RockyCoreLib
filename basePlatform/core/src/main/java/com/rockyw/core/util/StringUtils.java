package com.rockyw.core.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * 字符串相关
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/9/18
 */
public class StringUtils {
    private static final String PATTERN_INTEGER = "^[-\\+]?[\\d]*$";
    private static final String PATTERN_DOUBLE = "^[-\\+]?[.\\d]*$";

    /**
     * 去掉"-"号
     */
    public static String toAbs(String str) {

        return str.replaceAll("-", "");
    }

    /**
     * 功能描述：分割字符串
     *
     * @param str       String 原始字符串
     * @param splitsign String 分隔符
     * @return String[] 分割后的字符串数组
     */
    @SuppressWarnings("unchecked")
    public static String[] split(String str, String splitsign) {
        int index;
        if (str == null || splitsign == null) {
            return null;
        }
        ArrayList al = new ArrayList();
        while ((index = str.indexOf(splitsign)) != -1) {
            al.add(str.substring(0, index));
            str = str.substring(index + splitsign.length());
        }
        al.add(str);
        return (String[]) al.toArray(new String[0]);
    }

    /**
     * 功能描述：替换字符串
     *
     * @param from   String 原始字符串
     * @param to     String 目标字符串
     * @param source String 母字符串
     * @return String 替换后的字符串
     */
    public static String replace(String from, String to, String source) {
        if (source == null || from == null || to == null) {
            return null;
        }
        StringBuffer str = new StringBuffer("");
        int index = -1;
        while ((index = source.indexOf(from)) != -1) {
            str.append(source.substring(0, index) + to);
            source = source.substring(index + from.length());
            index = source.indexOf(from);
        }
        str.append(source);
        return str.toString();
    }

    /**
     * 保存文字到剪贴板
     *
     * @param context context
     * @param text    text
     */
    public static void copyToClipBoard(Context context, String text) {
        ClipData clipData = ClipData.newPlainText("url", text);
        ClipboardManager manager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        assert manager != null;
        manager.setPrimaryClip(clipData);
        Toast.makeText(context, "已复制到剪贴板", Toast.LENGTH_SHORT).show();
    }

    /**
     * 描述：是否是邮箱.
     *
     * @param str 指定的字符串
     * @return 是否是邮箱:是为true，否则false
     */
    public static boolean isEmail(String str) {
        boolean isEmail = false;
        String expr = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})$";

        if (str.matches(expr)) {
            isEmail = true;
        }
        return isEmail;
    }

    /**
     * 判断是否是手机号
     *
     * @param phone
     * @return
     */
    public static boolean isPhoneNumber(String phone) {
        if (TextUtils.isEmpty(phone)) {
            return false;
        }
        if (phone.length() != 11) {
            return false;
        }
        //#严格匹配的规则：^(13[0-9]|15[0-9]|153|15[6-9]|180|18[23]|18[5-9])\d{8}$
        //#放开匹配的规则：1开头的11位数字
        Pattern pattern = compile("^(1)\\d{10}$");
        Matcher matcher = pattern.matcher(phone);

        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    public static boolean isSmsVerifyCode(String code) {
        Pattern pattern = compile("d{6}");
        Matcher matcher = pattern.matcher(code);

        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    public static String getNumericScaleByCeil(String numberValue, int scale) {
        return new BigDecimal(numberValue).setScale(scale, BigDecimal.ROUND_CEILING).toString();
    }

    public static String getNumericScaleByFloor(String numberValue, int scale) {
        return new BigDecimal(numberValue).setScale(scale, BigDecimal.ROUND_FLOOR).toString();
    }

    /**
     * 规则1：至少包含大小写字母及数字中的一种
     * 是否包含
     *
     * @param str
     * @return
     */
    public static boolean isLetterOrDigit(String str) {
        //定义一个boolean值，用来表示是否包含字母或数字
        boolean isLetterOrDigit = false;
        for (int i = 0; i < str.length(); i++) {
            //用char包装类中的判断数字的方法判断每一个字符
            if (Character.isLetterOrDigit(str.charAt(i))) {
                isLetterOrDigit = true;
            }
        }
        String regex = "^[a-zA-Z0-9]+$";
        boolean isRight = isLetterOrDigit && str.matches(regex);
        return isRight;
    }

    /**
     * 规则2：至少包含大小写字母及数字中的两种
     * 是否包含
     *
     * @param str
     * @return
     */
    public static boolean isLetterDigit(String str) {
        //定义一个boolean值，用来表示是否包含数字
        boolean isDigit = false;
        //定义一个boolean值，用来表示是否包含字母
        boolean isLetter = false;
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {
                //用char包装类中的判断数字的方法判断每一个字符
                isDigit = true;
            } else if (Character.isLetter(str.charAt(i))) {
                //用char包装类中的判断字母的方法判断每一个字符
                isLetter = true;
            }
        }
        String regex = "^[a-zA-Z0-9]+$";
        boolean isRight = isDigit && isLetter && str.matches(regex);
        return isRight;
    }

    /**
     * 规则3：必须同时包含大小写字母及数字
     * 是否包含
     *
     * @param str
     * @return
     */
    public static boolean isContainAll(String str) {
        //定义一个boolean值，用来表示是否包含数字
        boolean isDigit = false;
        //定义一个boolean值，用来表示是否包含字母
        boolean isLowerCase = false;
        boolean isUpperCase = false;
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {
                //用char包装类中的判断数字的方法判断每一个字符
                isDigit = true;
            } else if (Character.isLowerCase(str.charAt(i))) {
                //用char包装类中的判断字母的方法判断每一个字符
                isLowerCase = true;
            } else if (Character.isUpperCase(str.charAt(i))) {
                isUpperCase = true;
            }
        }
        String regex = "^[a-zA-Z0-9]+$";
        boolean isRight = isDigit && isLowerCase && isUpperCase && str.matches(regex);
        return isRight;
    }

    /**
     * 判断EditText输入的数字、中文还是字母方法
     */
    public static void whatIsInput(Context context, String inputString) {

        Pattern p = compile("[0-9]*");
        Matcher m = p.matcher(inputString);
        if (m.matches()) {
            Toast.makeText(context, "输入的是数字", Toast.LENGTH_SHORT).show();
        }
        p = compile("[a-zA-Z]");
        m = p.matcher(inputString);
        if (m.matches()) {
            Toast.makeText(context, "输入的是字母", Toast.LENGTH_SHORT).show();
        }
        p = compile("[\u4e00-\u9fa5]");
        m = p.matcher(inputString);
        if (m.matches()) {
            Toast.makeText(context, "输入的是汉字", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 留零位
     *
     * @param result
     * @return
     */
    public static String transToPercent0(double result) {
        DecimalFormat df = new DecimalFormat("0%");
        return df.format(result);
    }

    /**
     * 留一位
     *
     * @param result
     * @return
     */
    public static String transToPercent1(double result) {
        DecimalFormat df = new DecimalFormat("0.0%");
        return df.format(result);
    }

    /**
     * 留两位
     *
     * @param result
     * @return
     */
    public static String transToPercent2(double result) {
        return transToPercent2(result, true);
    }

    public static String transToPercent2(double result, boolean withPercent) {
        if (withPercent) {
            DecimalFormat df = new DecimalFormat("0.00%");
            return df.format(result);
        } else {
            DecimalFormat df = new DecimalFormat("0.00");
            return df.format(result * 100);
        }
    }

    /**
     * 留4位
     *
     * @param result
     * @return
     */
    public static String transToPercent4(double result) {
        return transToPercent4(result, true);
    }


    public static String transToPercent4(double result, boolean withPercent) {
        if (withPercent) {
            DecimalFormat df = new DecimalFormat("0.0000%");
            return df.format(result);
        } else {
            DecimalFormat df = new DecimalFormat("0.0000");
            return df.format(result * 100);
        }
    }


    /**
     * 留4位
     *
     * @param result
     * @return
     */
    public static String transToThousandPercent4(double result) {
        return transToThousandPercent4(result, true);
    }

    public static String transToThousandPercent4(double result, boolean withPercent) {
        if (withPercent) {
            DecimalFormat df = new DecimalFormat("0.0000‰");
            return df.format(result);
        } else {
            DecimalFormat df = new DecimalFormat("0.0000");
            return df.format(result * 1000);
        }
    }

    /**
     * 判断整数（int）
     *
     * @param str
     * @return
     */
    public static boolean isInteger(String str) {
        if (null == str || "".equals(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile(PATTERN_INTEGER);
        return pattern.matcher(str).matches();
    }

    /**
     * 判断浮点数（double和float）
     *
     * @param str
     * @return
     */
    public static boolean isDouble(String str) {
        if (null == str || "".equals(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile(PATTERN_DOUBLE);
        return pattern.matcher(str).matches();
    }

    /**
     * 校验银行卡卡号
     * <p>
     * 校验过程：
     * 1、从卡号最后一位数字开始，逆向将奇数位(1、3、5等等)相加。
     * 2、从卡号最后一位数字开始，逆向将偶数位数字，先乘以2（如果乘积为两位数，将个位十位数字相加，即将其减去9），再求和。
     * 3、将奇数位总和加上偶数位总和，结果应该可以被10整除。
     */
    public static boolean checkBankCard(String bankCard) {
        if (bankCard.length() < 15 || bankCard.length() > 19) {
            return false;
        }
        char bit = getBankCardCheckCode(bankCard.substring(0, bankCard.length() - 1));
        if (bit == 'N') {
            return false;
        }
        return bankCard.charAt(bankCard.length() - 1) == bit;
    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     *
     * @param nonCheckCodeBankCard
     * @return
     */
    public static char getBankCardCheckCode(String nonCheckCodeBankCard) {
        if (nonCheckCodeBankCard == null || nonCheckCodeBankCard.trim().length() == 0
                || !nonCheckCodeBankCard.matches("\\d+")) {
            //如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeBankCard.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }

    public static <K, V> String handleMapParamToString(Map map) {

        Iterator<Map.Entry<K, V>> i = map.entrySet().iterator();

        StringBuilder sb = new StringBuilder();

        for (; ; ) {
            Map.Entry<K, V> m = i.next();
            K key = m.getKey();
            V value = m.getValue();
            sb.append(key);
            sb.append('=');
            sb.append(value);
            if (!i.hasNext()) {
                return sb.toString();
            }
            sb.append('&');
        }
    }

    /**
     * 加密银行卡的展示样式 12312142453452345  变成1234 1243 1235 1112
     * 就是加空格
     *
     * @param bankCard
     * @return
     */
    public static String formatCardNo(String bankCard) {
        if (TextUtils.isEmpty(bankCard)) {
            return "";
        }
        String regex = "(.{4})";
        return bankCard.replaceAll(regex, "$1 ");
    }

    public static double twoAfterPoint(double temp) {
        String strNum = String.valueOf(temp);
        String ss = "";
        int a = strNum.indexOf(".");
        if (a > 0) {
            //获取小数点后面的数字
            String dianAfter = strNum.substring(a + 1);
            if (dianAfter.length() >= 2) {
                ss = strNum.substring(0, a + 3);
            } else {
                ss = strNum + "0";
            }
        } else {
            ss = strNum + ".00";
        }
        return Double.parseDouble(ss);
    }

    public static void main(String[] args) {
        String orderNo = "aaa";
        String sign = "bbb";
        String returnUrl = "ccc";
        String orderStatus = "ddd";
        String bankCode = "eee";
        HashMap<String, String> map = new HashMap(5);
        map.put("orderNo", orderNo);
        map.put("sign", sign);
        map.put("returnUrl", returnUrl);
        map.put("orderStatus", orderStatus);
        map.put("bankCode", bankCode);
        System.out.println(handleMapParamToString(map));

        System.out.println(isLetterDigit("CHsissi1234567"));
        System.out.println(isLetterDigit("aaa"));
        System.out.println(isLetterDigit("AAA"));
        System.out.println(isLetterDigit("111aaa"));
        System.out.println(isLetterDigit("111AAA"));
        System.out.println(isLetterDigit("111AAA"));
        System.out.println(isLetterDigit("111AAAaaa"));
        System.out.println(isLetterDigit("111AAA@#$@"));
        System.out.println(isLetterDigit("111AAA@#$@啊啊"));

        System.out.println(transToPercent1(0.111111));
        System.out.println(transToPercent1(1111.111111));
        System.out.println(transToPercent2(1234.12345));
        System.out.println(transToPercent2(1234.12345, false));
    }
}


