package com.rockyw.core.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * @author wangxueming
 * @date 2018/12/12 下午5:22
 * @desc 高度会强制自适应。所有的item都展示，即不会滑动
 */
public class NoScrollRecyclerView extends RecyclerView {

    public NoScrollRecyclerView(Context context) {
        super(context);
    }

    public NoScrollRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScrollRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //#以下部分加上后，高度会强制自适应。所有的item都展示，即不会滑动  --《《《《
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
        //#----------------------------------------------------------》》》》
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
