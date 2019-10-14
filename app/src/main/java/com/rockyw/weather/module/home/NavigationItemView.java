package com.rockyw.weather.module.home;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rockyw.weather.R;

/**
 * 首页底部导航栏的布局
 *
 * @author wangxueming
 * @version 1.0.0
 * @date 2018/12/24
 */
public class NavigationItemView extends RelativeLayout {
    private Context mContext;
    private TextView mTextTv;
    private TextView mRedCircleTv;

    public NavigationItemView(Context context) {
        this(context, null);
    }

    public NavigationItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.main_home_bottom_navigation, this);
        mTextTv = findViewById(R.id.main_nav_text);
        mRedCircleTv = findViewById(R.id.main_nav_unread);
        setRedCircleNum(0);
    }

    public void setNavigationData(Data data) {
        if (data != null) {
            setRedCircleNum(data.getRedCircle());
            setIcon(data.getDrawTopResId());
            setText(data.getText());
            setTextColor(data.getTextColor());
        }
    }

    public void setRedCircleNum(int num) {
        if (mRedCircleTv != null) {
            if (num > 0) {
                mRedCircleTv.setVisibility(View.VISIBLE);
                mRedCircleTv.setText(num > 99 ? "99+" : String.valueOf(num));
            } else {
                mRedCircleTv.setVisibility(View.GONE);
            }
        }
    }

    public void setIcon(int resId) {
        if (mTextTv != null) {
            mTextTv.setCompoundDrawablesWithIntrinsicBounds(0, resId, 0, 0);
        }
    }

    public void setText(String text) {
        if (mTextTv != null) {
            mTextTv.setText(text);
        }
    }

    public void setTextColor(int color) {
        if (mTextTv != null && color != 0) {
            mTextTv.setTextColor(ContextCompat.getColor(getContext(), color));
        }
    }

    public void setSelect(boolean select) {
        if (mTextTv != null) {
            mTextTv.setSelected(select);
        }
    }

    public static class Data {
        /**
         * 默认状态,可见,点击效果切换fragment
         */
        public static final int STATUS_DEFAULT_FRAGMENT = 0;
        /**
         * 可见,自定义点击事件
         */
        public static final int STATUS_CLICK_CUSTOM = 1;
        /**
         * 不可见
         */
        public static final int STATUS_INVISIBLE = 2;

        private int drawTopResId;

        private int textColor;

        private String text;

        private Fragment fragment;

        private OnClickListener clickListener;

        private int fragmentPosition;

        private int redCircle;

        private int status = STATUS_DEFAULT_FRAGMENT;

        public Data() {
            status = STATUS_INVISIBLE;
        }

        public Data(int status) {
            this.status = status;
        }

        public Data(int drawTopResId, String text, Fragment fragment) {
            this.drawTopResId = drawTopResId;
            this.text = text;
            this.fragment = fragment;
            status = STATUS_DEFAULT_FRAGMENT;
        }

        public Data(int drawTopResId, int textColor, String text) {
            this(drawTopResId, textColor, text, null);
        }

        public Data(int drawTopResId, int textColor, String text, OnClickListener clickListener) {
            this.drawTopResId = drawTopResId;
            this.textColor = textColor;
            this.text = text;
            this.clickListener = clickListener;
            status = STATUS_CLICK_CUSTOM;
        }

        public int getFragmentPosition() {
            return fragmentPosition;
        }

        public void setFragmentPosition(int fragmentPosition) {
            this.fragmentPosition = fragmentPosition;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getDrawTopResId() {
            return drawTopResId;
        }

        public void setDrawTopResId(int drawTopResId) {
            this.drawTopResId = drawTopResId;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public Fragment getFragment() {
            return fragment;
        }

        public void setFragment(Fragment fragment) {
            this.fragment = fragment;
        }

        public int getRedCircle() {
            return redCircle;
        }

        public void setRedCircle(int redCircle) {
            this.redCircle = redCircle;
        }

        public int getTextColor() {
            return textColor;
        }

        public void setTextColor(int textColor) {
            this.textColor = textColor;
        }

        public OnClickListener getClickListener() {
            return clickListener;
        }

        public void setClickListener(OnClickListener clickListener) {
            this.clickListener = clickListener;
        }
    }

}
