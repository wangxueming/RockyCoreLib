package com.rockyw.couriershop.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.rockyw.core.widget.dialog.CenterBaseDialog;
import com.rockyw.couriershop.R;
import com.rockyw.couriershop.R2;

import butterknife.BindView;

/**
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/2/15
 */
public class VersionInfoDialog extends CenterBaseDialog {

    @BindView(R2.id.main_version_info_title)
    TextView mTitleTv;
    @BindView(R2.id.main_version_info_content)
    TextView mContentTv;
    @BindView(R2.id.main_version_info_btn_negative)
    TextView mNegativeBtn;
    @BindView(R2.id.main_version_info_btn_positive)
    TextView mPositiveBtn;

    private String mPositiveText;
    private String mNegativeText;
    private String mContentText;
    private String mTitleText;
    
    public VersionInfoDialog(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.main_dlg_version_info;
    }

    @Override
    public void initView(View view) {
        if (TextUtils.isEmpty(mTitleText)) {
            mTitleTv.setVisibility(View.GONE);
        } else {
            mTitleTv.setText(mTitleText);
            mTitleTv.setVisibility(View.VISIBLE);
        }
        mContentTv.setText(mContentText);
        if (!TextUtils.isEmpty(mPositiveText)) {
            mPositiveBtn.setText(mPositiveText);
        }
        if (!TextUtils.isEmpty(mNegativeText)) {
            mNegativeBtn.setText(mNegativeText);
        } else {
            mNegativeBtn.setVisibility(View.GONE);
        }
    }

    public interface ClickListener {
        /**
         * 版本信息的按钮的点击
         * @param dialog
         */
        void onClick(Dialog dialog);
    }

    public static class Builder {

        private VersionInfoDialog mDialog;

        public Builder(Context context) {
            mDialog = new VersionInfoDialog(context);
            mDialog.mNegativeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                }
            });
        }

        public Builder setPositiveText(String positiveText) {
            mDialog.setPositiveText(positiveText);
            return this;
        }

        public Builder setPositiveText(@StringRes int positiveTextResId) {
            mDialog.setPositiveText(mDialog.getString(positiveTextResId));
            return this;
        }

        public Builder setPositiveClickListener(final ClickListener clickListener) {
            mDialog.mPositiveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) {
                        clickListener.onClick(mDialog);
                    }
                }
            });
            return this;
        }

        public Builder setNegativeText(String negativeText) {
            mDialog.setNegativeText(negativeText);
            return this;
        }

        public Builder setNegativeText(@StringRes int negativeTextResId) {
            mDialog.setNegativeText(mDialog.getString(negativeTextResId));
            return this;
        }

        public Builder setNegativeClickListener(final ClickListener clickListener) {
            mDialog.mNegativeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) {
                        clickListener.onClick(mDialog);
                    }
                }
            });
            return this;
        }

        public Builder setContentText(String contentText) {
            mDialog.setContentText(contentText);
            return this;
        }

        public Builder setContentText(@StringRes int contentTextResId) {
            mDialog.setContentText(mDialog.getString(contentTextResId));
            return this;
        }

        public Builder setTitleText(String titleText) {
            mDialog.setTitleText(titleText);
            return this;
        }

        public Builder setTitleText(@StringRes int titleTextResId) {
            mDialog.setTitleText(mDialog.getString(titleTextResId));
            return this;
        }

        public Dialog build() {
            return mDialog;
        }

    }

    private void setNegativeText(String negativeText) {
        mNegativeText = negativeText;
    }

    private void setContentText(String contentTxt) {
        mContentText = contentTxt;
    }

    private void setTitleText(String title) {
        mTitleText = title;
    }

    private void setPositiveText(String positiveText) {
        mPositiveText = positiveText;
    }
}
