package com.rockyw.core.util;

/**
 * dp sp的转换工具
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/9/18
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

