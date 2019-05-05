package com.rocky.core.widget.verticalText.strategy;

import android.text.BoringLayout;
import android.text.Layout;
import android.text.TextPaint;
import android.text.TextUtils;

import com.rocky.core.widget.verticalText.StaticLayoutHelper;
import com.rocky.core.widget.verticalText.VerticalRollingTextView;

/**
 * 单行显示
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/4/17
 */
public class SingleLineStrategy implements IStrategy {
    @Override
    public VerticalRollingTextView.LayoutWithTextSize getLayout(float autoSizeMinTextSizeInPx,
                                                                float autoSizeMaxTextSizeInPx,
                                                                float autoSizeStepGranularityInPx,
                                                                int wantTextSize,
                                                                int width,
                                                                int height,
                                                                TextPaint paint,
                                                                int maxLines,
                                                                CharSequence text,
                                                                TextUtils.TruncateAt truncateAt) {

        BoringLayout.Metrics metrics = BoringLayout.isBoring(text, paint);

        Layout layout;

        if (null == metrics) {
            layout = StaticLayoutHelper.createStaticLayout(
                    text,
                    paint,
                    width,
                    Layout.Alignment.ALIGN_NORMAL,
                    1.0f,
                    0.0f,
                    false,
                    truncateAt,
                    width,
                    1);
        } else {
            layout = new BoringLayout(
                    text,
                    paint,
                    width,
                    Layout.Alignment.ALIGN_NORMAL,
                    1.0f,
                    0.0f,
                    metrics,
                    false,
                    truncateAt,
                    width);
        }

        VerticalRollingTextView.LayoutWithTextSize lt = new VerticalRollingTextView.LayoutWithTextSize();
        lt.layout = layout;
        lt.textSize = wantTextSize;
        return lt;
    }

    @Override
    public void reset() {

    }
}
