package com.rockyw.couriershop;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.rockyw.core.base.mvp.BaseVpActivity;
import com.rockyw.core.util.AppUtils;
import com.rockyw.core.util.logger.L;
import com.rockyw.core.widget.dialog.WinConfirmDialog;
import com.rockyw.projectcore.common.bean.VersionResponse;
import com.rockyw.projectcore.common.router.CommonServerData;
import com.rockyw.projectcore.common.router.RouterUrl;
import com.rockyw.projectcore.config.AppConfig;
import com.rockyw.couriershop.widget.VersionInfoDialog;

import butterknife.BindView;

/**
 * 起始页
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/1/21
 */
@Route(path = RouterUrl.SPLASH_PAGE)
public class SplashActivity extends BaseVpActivity<ISplashContract.View, ISplashContract.Presenter> implements ISplashContract.View {

    private static final int MIN_ANIMATION_DURATION = 5000;

    @BindView(R2.id.main_tv_service_type)
    TextView mServiceTypeTv;
    private long mStartCheckVersionTimeMillis;
    private Dialog mVersionInfoDlg;
    private Dialog mCheckInfoFailDlg;

    private boolean isCanClickAgain = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        startAppLogic();
        jumpToTestPage();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_activity_splash;
    }

    @Override
    protected void initTitle() {
    }

    @Override
    protected void initView() {
        if (BuildConfig.DEBUG) {
            int serviceType = AppConfigLauncher.SERVER_TYPE;
            if (serviceType == CommonServerData.T_SERVICE_DEBUG) {
                mServiceTypeTv.setText("测试服");
            } else if (serviceType == CommonServerData.T_SERVICE_PRESET) {
                mServiceTypeTv.setText("预发服");
            } else if (serviceType == CommonServerData.T_SERVICE_RELEASE) {
                mServiceTypeTv.setText("正式服");
            }
        }
    }

    @Override
    public ISplashContract.Presenter createPresenter() {
        return new SplashPresenter();
    }

    @Override
    public ISplashContract.View createView() {
        return this;
    }

    @Override
    public void handleVersionInfo(final VersionResponse response) {
        if (AppUtils.compareVersion(response.data.versionNumber, AppUtils.getVerName(getContext())) > 0) {
            //#说明服务器版本比我们的高
            boolean isForce = response.data.type == AppConfig.UPGRADE_FORCE;
            if (isForce) {
                mVersionInfoDlg = new VersionInfoDialog.Builder(getContext())
                        .setTitleText(response.data.title)
                        .setContentText(response.data.description.replace("&", "\n"))
                        .setNegativeText("")
                        .setPositiveText("去更新")
                        .setPositiveClickListener(new VersionInfoDialog.ClickListener() {
                            @Override
                            public void onClick(Dialog dialog) {
                            }
                        })
                        .build();
                mVersionInfoDlg.setCanceledOnTouchOutside(false);
                mVersionInfoDlg.show();
            } else {
                mVersionInfoDlg = new VersionInfoDialog.Builder(getContext())
                        .setTitleText(response.data.title)
                        .setContentText(response.data.description.replace("&", "\n"))
                        .setNegativeText("稍后再去")
                        .setPositiveText("去更新")
                        .setPositiveClickListener(new VersionInfoDialog.ClickListener() {
                            @Override
                            public void onClick(Dialog dialog) {
                            }
                        })
                        .setNegativeClickListener(new VersionInfoDialog.ClickListener() {
                            @Override
                            public void onClick(Dialog dialog) {
                                jumpToHome();
                            }
                        })
                        .build();
                mVersionInfoDlg.setCanceledOnTouchOutside(false);
                mVersionInfoDlg.show();
            }
        } else {
            jumpToHome();
        }
    }

    @Override
    public void obtainVersionInfoFailed(String code, String msg) {
        if (mCheckInfoFailDlg == null) {
            mCheckInfoFailDlg = new WinConfirmDialog.Builder(getContext())
                    .setTitleText("消息提示")
                    .setContentText(msg)
                    .setNegativeText("退出")
                    .setPositiveText("重试")
                    .setPositiveClickListener(new WinConfirmDialog.ClickListener() {
                        @Override
                        public void onClick(Dialog dialog) {
                            startAppLogic();
                        }
                    })
                    .setNegativeClickListener(new WinConfirmDialog.ClickListener() {
                        @Override
                        public void onClick(Dialog dialog) {
                            mCheckInfoFailDlg.cancel();
                            finish();
                        }
                    })
                    .build();
            mCheckInfoFailDlg.setCanceledOnTouchOutside(false);
        }
        if (!mCheckInfoFailDlg.isShowing()) {
            mCheckInfoFailDlg.show();
        }
    }

    /**
     * 应用启动逻辑
     */
    private void startAppLogic() {
//        getPresenter().checkVersion();
        mStartCheckVersionTimeMillis = System.currentTimeMillis();
        jumpToHome();
    }

    private void jumpToHome() {
        if (!isCanClickAgain) {
            return;
        }
        if (mCheckInfoFailDlg != null) {
            mCheckInfoFailDlg.dismiss();
        }
        if (mVersionInfoDlg != null) {
            mVersionInfoDlg.dismiss();
        }

        long obtainVersionInfoDuration = System.currentTimeMillis() - mStartCheckVersionTimeMillis;
        if (obtainVersionInfoDuration > MIN_ANIMATION_DURATION) {
            jumpToHomeInterval();
        } else {
            getWindow().getDecorView().postDelayed(new Runnable() {
                @Override
                public void run() {
                    jumpToHomeInterval();
                }
            }, obtainVersionInfoDuration);
        }
    }

    private void jumpToHomeInterval() {
        L.i("设备信息[UMENG_CHANNEL]:" + AppUtils.getAppMetaData(getContext(), "UMENG_CHANNEL"));
        ARouter.getInstance().build(RouterUrl.HOME).navigation();
        finish();
    }

    private void jumpToTestPage() {
        ARouter.getInstance().build(RouterUrl.HOME).greenChannel().withFlags(Intent.FLAG_ACTIVITY_NEW_TASK).navigation();
    }
}
