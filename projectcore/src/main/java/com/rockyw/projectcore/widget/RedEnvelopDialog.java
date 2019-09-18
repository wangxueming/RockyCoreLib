package com.rockyw.projectcore.widget;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.rockyw.core.widget.dialog.BaseCustomDialog;
import com.rockyw.projectcore.R;
import com.rockyw.projectcore.R2;
import com.rockyw.projectcore.service.buriedpoint.IBuriedPointService;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 弹出红包页的dialog的dialog
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/2/15
 */
public class RedEnvelopDialog extends BaseCustomDialog {

    @BindView(R2.id.core_tv_amount)
    TextView mAmountTv;
    @BindView(R2.id.core_tv_title)
    TextView mTitleTv;
    @BindView(R2.id.core_v_confirm)
    View mConfirmV;

    private String mType;

    public RedEnvelopDialog(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.core_dlg_red_envelop;
    }

    @Override
    public void initView(View view) {
    }

    @OnClick(R2.id.core_iv_close_dialog)
    public void clickCancel() {
        this.dismiss();
    }

    public void setType(String type) {
        this.mType = type;

        mTitleTv.setText(getTitleDesc(mType));
    }

    public interface ClickListener {
        /**
         * 状态点击
         * @param dialog
         */
        void onClick(Dialog dialog);
    }

    public static class Builder {

        private RedEnvelopDialog mDialog;

        public Builder(Context context) {
            mDialog = new RedEnvelopDialog(context);
        }

        public Builder setAmount(String amount) {
            mDialog.mAmountTv.setText(amount);
            return this;
        }

        public Builder setTitle(String title) {
            mDialog.mTitleTv.setText(title);
            return this;
        }


        public Builder setType(String type) {
            mDialog.setType(type);
            return this;
        }

        public Builder setConfirmListener(final RedEnvelopDialog.ClickListener clickListener) {
            mDialog.mConfirmV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) {
                        clickListener.onClick(mDialog);
                    }
                }
            });
            return this;
        }

        public Dialog build() {
            return mDialog;
        }
    }

    private String getTitleDesc(String type) {
        if(TextUtils.equals(IBuriedPointService.BP_REGISTER, type)) {
            return "新人注册大礼包";
        } else if(TextUtils.equals(IBuriedPointService.BP_CERTIFICATION, type)) {
            return "实名认证红包奖励";
        } else if(TextUtils.equals(IBuriedPointService.BP_BIND_CARD, type)) {
            return "首次绑卡成功奖励";
        } else if(TextUtils.equals(IBuriedPointService.BP_RECHARGE, type)) {
            return "首次充值成功奖励";
        } else  if(TextUtils.equals(IBuriedPointService.BP_INVEST, type)) {
            return "投资成功奖励";
        } else {
            return "红包奖励";
        }
    }
}
