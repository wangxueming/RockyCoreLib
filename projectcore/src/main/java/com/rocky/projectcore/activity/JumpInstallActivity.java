package com.rocky.projectcore.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rocky.core.base.mvc.BaseVcActivity;
import com.rocky.projectcore.R;
import com.rocky.projectcore.UrlServiceManager;
import com.rocky.projectcore.common.router.CommonAction;
import com.rocky.projectcore.common.router.RouterUrl;
import com.rocky.projectcore.publicserver.IPublicService;

import java.io.File;

/**
 * @author: WangXueMing
 * @version: 1.0.0
 * @date: 2019/1/21
 */
@Route(path = RouterUrl.OTHERS_JUMP_INSTALL)
public class JumpInstallActivity extends BaseVcActivity {

    String mTitle;
    int mProgress;
    String mFilePath;

    @Override
    protected int getLayoutId() {
        return R.layout.core_activity_jump_install;
    }

    @Override
    public void initData() {
        mTitle = getIntent().getStringExtra(CommonAction.COM_ACT_DATA_1);
        mProgress = getIntent().getIntExtra(CommonAction.COM_ACT_DATA_2, 0);
        mFilePath = getIntent().getStringExtra(CommonAction.COM_ACT_DATA_3);
        doRealInstall();
    }

    @Override
    public void onNewIntent(Intent intent) {
        mTitle = intent.getStringExtra(CommonAction.COM_ACT_DATA_1);
        mProgress = intent.getIntExtra(CommonAction.COM_ACT_DATA_2, 0);
        mFilePath = intent.getStringExtra(CommonAction.COM_ACT_DATA_3);
        doRealInstall();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void initTitle() {
    }

    @Override
    protected void initView() {
    }

    private void doRealInstall() {
        IPublicService publicService = UrlServiceManager.getPublicService();
        boolean isFinished = publicService.isDownloadFinished(mFilePath) || mProgress == 100;
        if (!isFinished || TextUtils.isEmpty(mFilePath)) {
            finish();
            return;
        }
        installAPK(mFilePath);
        finish();
    }

    private void installAPK(String fileSavePath) {
        File apkFile = new File(fileSavePath);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri apkUri = FileProvider.getUriForFile(this, "com.rocky.weather.fileprovider", apkFile);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        }
        startActivity(intent);
    }

}
