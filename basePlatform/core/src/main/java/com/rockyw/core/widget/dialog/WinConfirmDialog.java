package com.rockyw.core.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.rockyw.core.R;
import com.rockyw.core.R2;

import butterknife.BindView;

/**
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/1/7
 */
public class WinConfirmDialog extends CenterBaseDialog {

    @BindView(R2.id.base_dlg_tv_title)
    TextView mTitleTv;
    @BindView(R2.id.base_dlg_top_v_divide)
    View mDivideView;
    @BindView(R2.id.base_dlg_tv_positive)
    TextView mPositiveTv;
    @BindView(R2.id.base_dlg_tv_negative)
    TextView mNegativeTv;
    @BindView(R2.id.base_dlg_tv_content)
    TextView mContentTv;

    private String mPositiveText;
    private String mNegativeText;
    private String mContentText;
    private String mTitleText;

    public interface ClickListener {
        void onClick(Dialog dialog);
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

    public String getContentText() {
        return mContentText;
    }

    public void setContentText(String contentText) {
        mContentText = contentText;
    }

    private void setContentTextGravity(int gravity) {
        mContentTv.setGravity(gravity);
    }

    public String getTitleText() {
        return mTitleText;
    }

    public void setTitleText(String contentText) {
        mTitleText = contentText;
    }

    private WinConfirmDialog(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.base_win_dialog_confirm;
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
        mContentTv.setText(mContentText);
        if (!TextUtils.isEmpty(mPositiveText)) {
            mPositiveTv.setText(mPositiveText);
        }
        if (!TextUtils.isEmpty(mNegativeText)) {
            mNegativeTv.setText(mNegativeText);
        } else {
            mNegativeTv.setVisibility(View.GONE);
        }
    }

    public static class Builder {

        private WinConfirmDialog mDialog;

        public Builder(Context context) {
            mDialog = new WinConfirmDialog(context);
            mDialog.setNegativeText(mDialog.getString(R.string.base_cancel));
            mDialog.setPositiveText(mDialog.getString(R.string.base_confirm));
            mDialog.mNegativeTv.setOnClickListener(new View.OnClickListener() {
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
            mDialog.mPositiveTv.setOnClickListener(new View.OnClickListener() {
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
            mDialog.mNegativeTv.setOnClickListener(new View.OnClickListener() {
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

        public Builder setContentTextGravity(int gravity) {
            mDialog.setContentTextGravity(gravity);
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

}
