package com.rocky.projectcore.widget.popup;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.rocky.projectcore.R;
import com.rocky.projectcore.R2;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 公共的弹窗
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/4/8
 */
public abstract class AbstractCommonPop extends PopupWindow {

    protected View mContentView;
    protected Context mContext;

    @Override
    public View getContentView() {
        return mContentView;
    }

    public AbstractCommonPop(Context context) {
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContentView = inflater.inflate(getLayoutId(), null);
        setContentView(mContentView);
        ButterKnife.bind(this, mContentView);
        setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        setOutsideTouchable(true);
        setAnimationStyle(R.style.core_CommonPopupWindowAnimation);
        update();

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x80000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        setBackgroundDrawable(dw);
    }

    public Context getContext() {
        return mContext;
    }

    protected String getResString(@StringRes int resId) {
        return mContext.getString(resId);
    }

    protected int getResColor(@ColorRes int resId) {
        return ContextCompat.getColor(getContext(), resId);
    }

    /**
     * 自定义的弹窗主体View
     *
     * @return
     */
    public abstract int getLayoutId();

    @OnClick(R2.id.core_mask)
    public void clickMaskView() {
        dismiss();
    }
}
