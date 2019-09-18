package com.rockyw.projectcore.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.rockyw.projectcore.R;
import com.rockyw.projectcore.R2;
import com.rockyw.core.widget.dialog.CenterBaseDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/1/7
 */
public class ImgVerifyCodeDialog extends CenterBaseDialog {

    @BindView(R2.id.core_iv_close_dialog)
    ImageView mCloseIv;
    @BindView(R2.id.core_dlg_top_v_divide)
    View mDivideView;
    @BindView(R2.id.core_dlg_tv_positive)
    TextView mPositiveTv;
    @BindView(R2.id.core_dlg_tv_negative)
    TextView mNegativeTv;
    @BindView(R2.id.core_et_verify_code)
    EditText mVerifyCodeEt;
    @BindView(R2.id.core_iv_verify_code)
    ImageView mVerifyCodeIv;

    private String mPositiveText;
    private String mNegativeText;

    public interface ClickListener {
        void onClick(ImgVerifyCodeDialog dialog);
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

    public void setVerifyCodeImgBitmap(Bitmap bitmap) {
        mVerifyCodeIv.setImageBitmap(bitmap);
    }

    public String getEnteredVerifyCode() {
        return mVerifyCodeEt.getText().toString();
    }

    public void clearVerifyCode() {
        mVerifyCodeEt.setText("");
    }

    @OnClick(R2.id.core_iv_close_dialog)
    public void clickClose() {
        this.dismiss();
    }

    private ImgVerifyCodeDialog(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.core_dlg_img_verify_code;
    }

    @Override
    public void initView(View view) {
        mDivideView.setVisibility(View.VISIBLE);
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

        private ImgVerifyCodeDialog mDialog;

        public Builder(Context context) {
            mDialog = new ImgVerifyCodeDialog(context);
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

        public Builder setImageVerifyCodeClickListener(final ClickListener listener) {
            mDialog.mVerifyCodeIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onClick(mDialog);
                    }
                }
            });
            return this;
        }

        public ImgVerifyCodeDialog build() {
            return mDialog;
        }
    }

}
