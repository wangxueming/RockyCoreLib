package com.rockyw.projectcore.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.rockyw.core.widget.dialog.CenterBaseDialog;
import com.rockyw.projectcore.R;
import com.rockyw.projectcore.R2;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/2/15
 */
public class SetDealHistoryDialog extends CenterBaseDialog {

    @BindView(R2.id.core_dlg_tv_title)
    TextView mTitleTv;
    @BindView(R2.id.core_dlg_top_v_divide)
    View mDivideView;
    @BindView(R2.id.core_dlg_tv_positive)
    TextView mPositiveTv;
    @BindView(R2.id.core_dlg_tv_negative)
    TextView mNegativeTv;
    @BindView(R2.id.core_tv_history_1)
    TextView mHistory1Tv;
    @BindView(R2.id.core_tv_history_2)
    TextView mHistory2Tv;
    @BindView(R2.id.core_tv_history_3)
    TextView mHistory3Tv;
    @BindView(R2.id.core_ll_empty_container)
    TextView mEmptyTv;

    private String mPositiveText;
    private String mNegativeText;
    private String mContentText;
    private String mTitleText;
    private List<String> mData = new ArrayList<>();

    public SetDealHistoryDialog(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.core_set_deal_history_dialog;
    }

    @Override
    public void initView(View view) {
        if (TextUtils.isEmpty(mTitleText)) {
            mTitleTv.setVisibility(View.GONE);
            mDivideView.setVisibility(View.GONE);
        } else {
            mTitleTv.setText(mTitleText);
            mTitleTv.setVisibility(View.VISIBLE);
            mDivideView.setVisibility(View.VISIBLE);
        }
        mEmptyTv.setVisibility(mData.size()> 0 ? View.INVISIBLE : View.VISIBLE);
        if (mData.size()> 0) {
            mHistory1Tv.setText(mData.get(0));
        }
        if (mData.size()> 1) {
            mHistory2Tv.setText(mData.get(1));
        }
        if (mData.size()> 2) {
            mHistory3Tv.setText(mData.get(2));
        }
        if (!TextUtils.isEmpty(mPositiveText)) {
            mPositiveTv.setText(mPositiveText);
        }
        if (!TextUtils.isEmpty(mNegativeText)) {
            mNegativeTv.setText(mNegativeText);
        } else {
            mNegativeTv.setVisibility(View.GONE);
        }
    }

    public String getPositiveText() {
        return mPositiveText;
    }

    public void setPositiveText(String positiveText) {
        mPositiveText = positiveText;
    }

    public String getNegativeText() {
        return mNegativeText;
    }

    public void setNegativeText(String negativeText) {
        mNegativeText = negativeText;
    }

    public String getTitleText() {
        return mTitleText;
    }

    public void setTitleText(String contentText) {
        mTitleText = contentText;
    }

    OnClickListener mListener;

    public interface OnClickListener {
        /**
         * 确认支付按钮被点击
         *
         * @param dialog
         */
        void onClick(Dialog dialog);
    }

    public static class Builder {
        SetDealHistoryDialog dialog;

        public Builder(Context context) {
            dialog = new SetDealHistoryDialog(context);
        }

        public SetDealHistoryDialog.Builder setData(List<String> data) {
            dialog.mData.clear();
            if (data != null && !data.isEmpty()) {
                dialog.mData.addAll(data);
            }
            return this;
        }
        public SetDealHistoryDialog.Builder setTitleText(String titleText) {
            dialog.setTitleText(titleText);
            return this;
        }

        public SetDealHistoryDialog.Builder setTitleText(@StringRes int titleTextResId) {
            dialog.setTitleText(dialog.getString(titleTextResId));
            return this;
        }


        public SetDealHistoryDialog.Builder setPositiveClickListener(SetDealHistoryDialog.OnClickListener clickListener) {
            dialog.mPositiveTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) {
                        clickListener.onClick(dialog);
                    }
                }
            });
            return this;
        }


        public Dialog build() {
            return dialog;
        }
    }
}
