package com.rocky.core.util;

/**
 * @author: wangxueming
 * @description: java类作用描述
 * @createDate: 2018/12/12 下午5:06
 * @updateUser: 更新者
 * @updateDate: 2018/12/12 下午5:06
 * @updateRemark: 更新说明
 * @version: 1.0
 */

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class DimensionUtil {

    private static DisplayMetrics sDisplayMetrics;

    private DimensionUtil() {}

    public static void init(Context context) {
        sDisplayMetrics = context.getResources().getDisplayMetrics();
    }

    public static float dp(float dpValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, sDisplayMetrics);
    }

    public static float sp(float spValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, sDisplayMetrics);
    }

}

