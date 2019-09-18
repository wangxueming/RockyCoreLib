package com.rockyw.projectcore.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.rockyw.core.util.KeyboardUtils;
import com.rockyw.core.widget.dialog.CenterBaseDialog;
import com.rockyw.projectcore.R;
import com.rockyw.projectcore.R2;
import com.rockyw.projectcore.util.MoneyUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 投资确认页
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/1/18
 */
public class EnterTradePwdDialog extends CenterBaseDialog {

    @BindView(R2.id.product_trade_pwd)
    EditText pwdEt;
    @BindView(R2.id.product_tv_amount)
    TextView amountTv;
    @BindView(R2.id.product_tv_cancel)
    TextView cancelTv;
    @BindView(R2.id.product_tv_confirm)
    TextView confirmTv;

    public double amount;

    public EnterTradePwdDialog(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.core_enter_trade_pwd;
    }

    @Override
    public void initView(View view) {
        if(amount == -1){
            amountTv.setVisibility(View.GONE);
        } else {
            amountTv.setVisibility(View.VISIBLE);
            amountTv.setText("￥ " + MoneyUtils.roundDown(amount, 2));
        }
    }

    @OnClick(R2.id.product_tv_cancel)
    public void clickCancel() {
        this.dismiss();
    }

    @OnClick(R2.id.product_tv_confirm)
    public void clickConfirm() {
        if (mListener != null) {
            mListener.onClick(this, pwdEt.getText().toString());
        }
    }

    @Override
    public void dismiss() {
        pwdEt.setText("");
        KeyboardUtils.hideSoftInput(pwdEt);
        super.dismiss();
    }

    OnClickListener mListener;

    public interface OnClickListener {
        /**
         * 确认支付按钮被点击
         *
         * @param dialog
         * @param pwd
         */
        void onClick(Dialog dialog, String pwd);
    }

    public void setOnClickListener(OnClickListener listener) {
        mListener = listener;
    }

    public static class Builder {
        EnterTradePwdDialog dialog;

        public Builder(Context context) {
            dialog = new EnterTradePwdDialog(context);
        }

        public Builder setAmount(double amount) {
            dialog.amount = amount;
            return this;
        }

        public Builder setPositiveClickListener(OnClickListener listener) {
            dialog.mListener = listener;
            return this;
        }

        public EnterTradePwdDialog create() {
            return dialog;
        }
    }
}
