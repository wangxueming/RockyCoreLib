package com.rockyw.core.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.rockyw.core.util.DimensionUtil;

/**
 * 通用 RecyclerView 分割线
 * 适用情况不断完善中..目前仅支持横向，纵向日后再说
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2018/12/25
 */
public class CommonItemDecoration extends RecyclerView.ItemDecoration {

    /**
     * 默认分割线高度
     */
    private static final int DEFAULT_HEIGHT = 1;

    protected Drawable mDivider;
    protected int mDividerHeight = DEFAULT_HEIGHT;
    protected int mMarginStart;
    protected int mMarginEnd;
    protected int mBackgroundColor;
    protected boolean showFirstDivider = true;
    protected boolean showLastDivider = true;

    public CommonItemDecoration(Context context, int dividerColor, float dividerHeight, int marginStart, int marginEnd) {
        this(context, Color.TRANSPARENT, dividerColor, dividerHeight, marginStart, marginEnd);
    }

    public CommonItemDecoration(Context context, int backgroundColor, int dividerColor, float dividerHeight, int marginStart, int marginEnd) {
        this(context, Color.TRANSPARENT, dividerColor, dividerHeight, marginStart, marginEnd, true, true);
    }

    public CommonItemDecoration(Context context, int backgroundColor, int dividerColor, float dividerHeight, int marginStart, int marginEnd, boolean showFirstDivider, boolean showLastDivider) {
        this.mBackgroundColor = backgroundColor;
        mDivider = createDividerDrawable(context, dividerColor);
        this.mDividerHeight = (int) DimensionUtil.dp(dividerHeight);
        this.mMarginStart = (int) DimensionUtil.dp(marginStart);
        this.mMarginEnd = (int) DimensionUtil.dp(marginEnd);
        this.showFirstDivider = showFirstDivider;
        this.showLastDivider = showLastDivider;
    }

    public GradientDrawable createDividerDrawable(Context context, int dividerColor) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setColor(dividerColor);
        return drawable;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        c.drawColor(mBackgroundColor);
        int left = parent.getPaddingLeft() + mMarginStart;
        int right = parent.getWidth() - parent.getPaddingRight() - mMarginEnd;

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount - 1; i++) {
            View child = parent.getChildAt(i);
            if (child.getVisibility() == View.GONE) {
                continue;
            }
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mDividerHeight;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (showFirstDivider && showLastDivider) {
            outRect.set(0, 0, 0, mDividerHeight);
        } else if (showFirstDivider && !showLastDivider) {
            int childAdapterPosition = parent.getChildAdapterPosition(view);
            int lastCount = parent.getAdapter().getItemCount() - 1;
            if (childAdapterPosition == lastCount) {
                outRect.set(0, 0, 0, 0);
                return;
            }
            outRect.set(0, 0, 0, mDividerHeight);
        } else if (!showFirstDivider && showLastDivider) {
            int childAdapterPosition = parent.getChildAdapterPosition(view);
            if (childAdapterPosition == 0) {
                outRect.set(0, 0, 0, 0);
                return;
            }
            outRect.set(0, 0, 0, mDividerHeight);
        } else {
            int childAdapterPosition = parent.getChildAdapterPosition(view);
            if (childAdapterPosition == 0) {
                outRect.set(0, 0, 0, 0);
                return;
            }

            int lastCount = parent.getAdapter().getItemCount() - 1;
            if (childAdapterPosition == lastCount) {
                outRect.set(0, 0, 0, 0);
                return;
            }
            outRect.set(0, 0, 0, mDividerHeight);
        }
    }

}