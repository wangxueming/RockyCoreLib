package com.rocky.core.widget.verticalText.strategy;

import android.text.TextPaint;
import android.text.TextUtils;

import com.rocky.core.widget.verticalText.VerticalRollingTextView;

/**
 * 垂直滚动策略类
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/4/17
 */
public interface IStrategy {

    VerticalRollingTextView.LayoutWithTextSize getLayout(float autoSizeMinTextSizeInPx,
                                                         float autoSizeMaxTextSizeInPx,
                                                         float autoSizeStepGranularityInPx,
                                                         int wantTextSize,
                                                         int width,
                                                         int height,
                                                         TextPaint paint,
                                                         int maxLines,
                                                         CharSequence text,
                                                         TextUtils.TruncateAt mTruncateAt);

    void reset();
}
