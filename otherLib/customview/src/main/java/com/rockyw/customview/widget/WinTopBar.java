package com.rockyw.customview.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.TouchDelegate;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rockyw.customview.R;

/**
 * @author: wangxueming
 * @version: 1.0.0
 * @date: 2018/12/20
 */
public class WinTopBar extends RelativeLayout {

    private RelativeLayout mContainer;
    private TextView mTitleTv;
    private TextView mRightTv;
    private TextView mLeftTv;
    private View mDividerView;

    public WinTopBar(Context context) {
        this(context, null);
    }

    public WinTopBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WinTopBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.cv_win_top_bar, this, true);
        findViews();
        readAttrs(attrs);
        setDefaultEvents();
    }

    private void findViews() {
        mContainer = findViewById(R.id.cv_ll_win_top_bar_container);
        mTitleTv = findViewById(R.id.cv_tv_title);
        mRightTv = findViewById(R.id.cv_tv_right);
        mLeftTv = findViewById(R.id.cv_tv_left);
        mDividerView = findViewById(R.id.cv_view_divider);
        expendTouchArea(mLeftTv, 10);
    }

    /**
     * read attrs from xml
     */
    private void readAttrs(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.cusview_WinTopBar);

        mContainer.setBackgroundResource(a.getResourceId(R.styleable.cusview_WinTopBar_bar_background, R.color.white));
        //title
        mTitleTv.setText(a.getString(R.styleable.cusview_WinTopBar_bar_titleText));
        mTitleTv.setTextColor(a.getColor(R.styleable.cusview_WinTopBar_bar_titleTextColor, ContextCompat.getColor(getContext(), R.color.gray_34333C)));
        // right
        mRightTv.setText(a.getString(R.styleable.cusview_WinTopBar_bar_rightText));
        Drawable rightDrawable = a.getDrawable(R.styleable.cusview_WinTopBar_bar_rightIcon);
        mRightTv.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, rightDrawable, null);
        mRightTv.setTextColor(a.getColor(R.styleable.cusview_WinTopBar_bar_rightTextColor, ContextCompat.getColor(getContext(), R.color.green_8ECF93)));

        // left
        mLeftTv.setText(a.getString(R.styleable.cusview_WinTopBar_bar_leftText));
        Drawable leftDrawable = a.getDrawable(R.styleable.cusview_WinTopBar_bar_leftIcon);
        if (leftDrawable != null) {
            mLeftTv.setCompoundDrawablesRelativeWithIntrinsicBounds(leftDrawable, null, null, null);
        }
        mLeftTv.setTextColor(a.getColor(R.styleable.cusview_WinTopBar_bar_leftTextColor, Color.BLACK));
        mLeftTv.setVisibility(a.getInt(R.styleable.cusview_WinTopBar_bar_leftVisibility, VISIBLE));

        // divider
        mDividerView.setVisibility(a.getBoolean(R.styleable.cusview_WinTopBar_bar_showDivider, true) ? VISIBLE : GONE);

        a.recycle();
    }

    public void setOnRightClickListener(View.OnClickListener listener) {
        mRightTv.setOnClickListener(listener);
    }

    public void setOnLeftClickListener(View.OnClickListener listener) {
        mLeftTv.setOnClickListener(listener);
    }

    public void setTitleText(String s) {
        mTitleTv.setText(s);
    }

    public void setTitleText(@StringRes int resId) {
        mTitleTv.setText(resId);
    }

    public void setTitleColor(int color) {
        mTitleTv.setTextColor(ContextCompat.getColor(getContext(), color));
    }

    public void setTitleBackground(int backgroundId) {
        mContainer.setBackgroundResource(backgroundId);
    }

    public void setRightText(String s) {
        mRightTv.setVisibility(VISIBLE);
        mRightTv.setText(s);
    }

    public void setRightText(@StringRes int resId) {
        mRightTv.setVisibility(VISIBLE);
        mRightTv.setText(resId);
    }

    public void setRightTextColor(int color) {
        mRightTv.setTextColor(ContextCompat.getColor(getContext(), color));
    }

    public void setRightIcon(@DrawableRes int resId) {
        mRightTv.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, resId, 0);
    }

    public void setLeftIcon(@DrawableRes int resId) {
        mLeftTv.setCompoundDrawablesRelativeWithIntrinsicBounds(resId, 0, 0, 0);
    }

    public String getTitleText() {
        return mTitleTv.getText().toString();
    }

    public void setLeftVisibility(int visibility) {
        mLeftTv.setVisibility(visibility);
    }

    public boolean isLeftVisibility() {
        return mLeftTv.getVisibility() == View.VISIBLE;
    }

    public void setRightVisibility(int visibility) {
        mRightTv.setVisibility(visibility);
    }

    public void setShowDivider(boolean show) {
        mDividerView.setVisibility(show ? VISIBLE : GONE);
    }

    private void setDefaultEvents() {
        setOnLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getContext() instanceof Activity) {
                    ((Activity) getContext()).onBackPressed();
                }
            }
        });
    }

    private void expendTouchArea(final View view, final int expendSize) {
        if (view != null) {
            final View parentView = (View) view.getParent();

            parentView.post(new Runnable() {
                @Override
                public void run() {
                    Rect rect = new Rect();
                    //如果太早执行本函数，会获取rect失败，因为此时UI界面尚未开始绘制，无法获得正确的坐标
                    view.getHitRect(rect);
                    rect.left -= expendSize;
                    rect.top -= expendSize;
                    rect.right += expendSize;
                    rect.bottom += expendSize;
                    parentView.setTouchDelegate(new TouchDelegate(rect, view));
                }
            });
        }
    }
}

