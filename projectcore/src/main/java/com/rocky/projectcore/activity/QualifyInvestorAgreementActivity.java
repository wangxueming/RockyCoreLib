package com.rocky.projectcore.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.rocky.core.BaseCountDownTimer;
import com.rocky.core.base.mvc.BaseVcActivity;
import com.rocky.core.util.SpannableStringUtils;
import com.rocky.customview.widget.WinTopBar;
import com.rocky.projectcore.R;
import com.rocky.projectcore.R2;
import com.rocky.projectcore.UrlServiceManager;
import com.rocky.projectcore.common.bean.QualifyInvestorResponse;
import com.rocky.projectcore.common.router.CommonAction;
import com.rocky.projectcore.common.router.RouterUrl;
import com.rocky.projectcore.login.IAccountService;
import com.rocky.projectcore.net.CommonObserver;
import com.rocky.projectcore.net.Server;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author: WangXueMing
 * @version: 1.0.0
 * @date: 2019/1/21
 */
@Route(path = RouterUrl.COMMON_QUALIFY_INVESTOR_AGREEMENT)
public class QualifyInvestorAgreementActivity extends BaseVcActivity {

    public static final int TOTAL_TIME = 11000;
    public static final int ONCE_TIME = 1000;

    @BindView(R2.id.core_tv_qualify_investor_summary1)
    TextView mInvestorSummary1Tv;
    @BindView(R2.id.core_tv_qualify_investor_summary2)
    TextView mInvestorSummary2Tv;
    @BindView(R2.id.core_btn_confirm)
    Button mConfirmBtn;
    @BindView(R2.id.core_cb_agreement)
    CheckBox mNoviceCb;
    @BindView(R2.id.core_top_bar)
    WinTopBar mTopBar;

    private BaseCountDownTimer mCountDownTimer;

    private boolean isRegisterFlag;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        initCountDownTimer(mConfirmBtn);
        mCountDownTimer.start();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.core_activity_qualify_investor;
    }

    @Override
    public void initData() {
        isRegisterFlag = getIntent().getBooleanExtra(CommonAction.COM_ACT_DATA_1, true);
    }

    @Override
    protected void initTitle() {
    }

    @Override
    protected void initView() {
        if (isRegisterFlag) {
            mTopBar.setLeftVisibility(View.VISIBLE);
        } else {
            mTopBar.setLeftVisibility(View.INVISIBLE);
        }
        SpannableStringBuilder investSummary1 = SpannableStringUtils
                .getBuilder(getContext())
                .append("一、投资者或其家庭名下金融资产")
                .append("不低于50万元人民币。\n\n")
                .setForegroundColor(ContextCompat.getColor(getContext(), R.color.base_orange_F0932E))
                .create();
        mInvestorSummary1Tv.setText(investSummary1);
        SpannableStringBuilder investSummary2 = SpannableStringUtils
                .getBuilder(getContext())
                .append("二、具有")
                .append("2年以上")
                .setForegroundColor(ContextCompat.getColor(getContext(), R.color.base_orange_F0932E))
                .append("金融产品投资经历或者")
                .append("2年以上")
                .setForegroundColor(ContextCompat.getColor(getContext(), R.color.base_orange_F0932E))
                .append("金融行业及相关工作经历。\n\n")
                .create();
        mInvestorSummary2Tv.setText(investSummary2);

        mNoviceCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                mConfirmBtn.setEnabled(isChecked);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (isRegisterFlag) {
            super.onBackPressed();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cancelTimer();
    }

    @OnClick(R2.id.core_btn_confirm)
    public void clickConfirm() {
        if (isRegisterFlag) {
            jumpToRegister();
        } else {
            confirmQualifyInvestor();
        }
    }

    private void cancelTimer() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }

    private void initCountDownTimer(final TextView tvCountDownTimer) {
        mCountDownTimer = new BaseCountDownTimer(TOTAL_TIME, ONCE_TIME) {
            @Override
            public void onStart(long startTime) {
                tvCountDownTimer.setEnabled(false);
                tvCountDownTimer.setClickable(false);
                mNoviceCb.setEnabled(false);
            }

            @Override
            public void onTick(long millisUntilFinished) {
                String value = String.valueOf((int) (millisUntilFinished / 1000));
                tvCountDownTimer.setText(String.format("%ss", value));
                tvCountDownTimer.setEnabled(false);
                mNoviceCb.setEnabled(false);
            }

            @Override
            public void onFinish() {
                tvCountDownTimer.setClickable(true);
                tvCountDownTimer.setEnabled(false);
                tvCountDownTimer.setText("确定");
                mNoviceCb.setEnabled(true);
            }

            @Override
            public void onCancel() {
                if (tvCountDownTimer != null) {
                    tvCountDownTimer.setClickable(true);
                    tvCountDownTimer.setEnabled(false);
                    tvCountDownTimer.setText("确定");
                }
                if (mNoviceCb != null) {
                    mNoviceCb.setEnabled(false);
                }
            }
        };
    }

    private void confirmQualifyInvestor() {
        IAccountService accountService = UrlServiceManager.getAccountService();
        String token = accountService.getUserInfo(getContext()).loginUserInfo.accessToken;
        addDisposable(Server.getServerApi().setQualifyInvestorInfo(token, QualifyInvestorResponse.QUA_INV_CONFIRMED)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new CommonObserver<QualifyInvestorResponse>(this) {
                                   @Override
                                   public void onSuccess(QualifyInvestorResponse response) {
                                       finish();
                                   }
                               }
                ));
    }

    private void jumpToRegister() {
        ARouter.getInstance().build(RouterUrl.LOGIN_REGISTER).greenChannel().navigation();
        finish();
    }
}
