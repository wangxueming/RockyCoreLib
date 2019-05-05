package com.rocky.core.util;

import com.orhanobut.logger.Logger;

import java.security.MessageDigest;
import java.util.Random;

/**
 * MD5工具类
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/4/17
 */
public class Md5 {

    // ----------------------- MD5 start-----------------------------------//

    public static String MD5Encode(String origin, String charsetName) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetName == null || "".equals(charsetName)) {
                resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
            } else {
                resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetName)));
            }
        } catch (Exception exception) {
        }
        Logger.i("================MD5：" + resultString.toString());
        return resultString;
    }

    private static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }

        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n += 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return HEX_DIGITS[d1] + HEX_DIGITS[d2];
    }

    private static final String HEX_DIGITS[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    // ----------------------- MD5 end-----------------------------------

    /**
     * 生产appKey
     *
     * @param appId
     * @param timeLong
     * @return
     */
    public static String creatAppKey(String appId, long timeLong) {
        Random ran = new Random();
        String result = appId + timeLong + ran.nextInt(1000);
        return MD5Encode(result, "UTF-8");
    }

    /**
     * 生产openId
     *
     * @param aid      用户id
     * @param appKey   应用密钥
     * @param deviceId 设备唯一标识
     *                 当前请求时间
     * @return
     */
    public static String creatOpendId(String aid, String appKey, String deviceId) {
        Random ran = new Random();
        String result = aid + appKey + deviceId;
        return MD5Encode(result, "UTF-8");
    }

}
