package com.rockyw.projectcore.util;

import android.text.TextUtils;

/**
 * WinFae自定义的OtherUtils库，用于放一些杂七杂八，不好归类。但又是通用的
 *
 * @author: WangXueMing
 * @version: 1.0.0
 * @date: 2019/1/29
 */
public class WfOtherUtils {
    public static boolean isPwdLengthValid(String pwd) {
        if (TextUtils.isEmpty(pwd)) {
            return false;
        }
        int length = pwd.length();
        return (length >= 6 && length <= 16);
    }
}
