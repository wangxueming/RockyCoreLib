package com.rocky.projectcore.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rocky.core.util.AppUtils;
import com.rocky.core.util.Tip;
import com.rocky.core.util.logger.L;
import com.rocky.net.download.FileDownloadCallback;
import com.rocky.net.download.OkHttpManager;
import com.rocky.projectcore.R;
import com.rocky.projectcore.UrlServiceManager;
import com.rocky.projectcore.activity.JumpInstallActivity;
import com.rocky.projectcore.common.router.CommonAction;
import com.rocky.projectcore.common.router.RouterUrl;
import com.rocky.projectcore.config.AppConfig;
import com.rocky.projectcore.publicserver.IPublicService;

import java.io.File;


/**
 * 用来下载apk的专用intent Service
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/3/6
 */
@Route(path = RouterUrl.OTHERS_DOWNLOAD_APK)
public class DownloadService extends IntentService {

    private static final int NOTIFY_ID = 9910023;

    private Object mLocker = new Object();

    public DownloadService() {
        super("DownloadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        String urlToDownload = intent.getStringExtra(CommonAction.COM_ACT_DATA_1);
        String versionName = intent.getStringExtra(CommonAction.COM_ACT_DATA_2);

        String apkName = AppConfig.WINFAE_APK_NAME + versionName + "." + AppConfig.WINFAE_APK_EXTENSION_NAME;
        String filePath = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + "/rocky_apk/" + AppUtils.getAppPackageName() + "/" + apkName;
        OkHttpManager.download(urlToDownload, new File(filePath), new FileDownloadCallback() {
            @Override
            public void onStart() {
                L.d("onStart" + filePath);
                IPublicService publicService = UrlServiceManager.getPublicService();
                publicService.notifyDownloadStart(filePath);

                getNotificationManager().notify(NOTIFY_ID, getNotification("下载中", 0, filePath));
            }

            @Override
            public void onProgress(float downloaded, long total) {
                L.d("downloaded: " + downloaded + "; total: " + total);
                IPublicService publicService = UrlServiceManager.getPublicService();
                publicService.notifyDownloadProgress(downloaded, total);

                getNotificationManager().notify(NOTIFY_ID, getNotification("下载中", (int) (downloaded / total * 100), filePath));
            }

            @Override
            public void onFailure(String msg) {
                L.d("onFailure" + msg + " path=" + filePath);
                IPublicService publicService = UrlServiceManager.getPublicService();
                publicService.notifyDownloadFailure(msg);

                getNotificationManager().notify(NOTIFY_ID, getNotification("下载失败", -1, filePath));
                Tip.onNotice(msg);
            }

            @Override
            public void onDone() {
                IPublicService publicService = UrlServiceManager.getPublicService();
                publicService.notifyDownloadSuccess(filePath);

                getNotificationManager().notify(NOTIFY_ID, getNotification("下载成功", 100, filePath));
                installAPK(filePath);
            }
        });


    }

    /**
     * 安装apk
     *
     * @param fileSavePath
     */
    private void openAPK(String fileSavePath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            data = FileProvider.getUriForFile(this, "com.rocky.weather.fileprovider", new File(fileSavePath));
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            data = Uri.fromFile(new File(fileSavePath));
        }

        intent.setDataAndType(data, "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        startActivity(intent);
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


    private NotificationManager getNotificationManager() {
        return (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    private Notification getNotification(String title, int progress) {
        return getNotification(title, progress, "");
    }

    private Notification getNotification(String title, int progress, String filePath) {
        synchronized (mLocker) {
            Intent intent = new Intent();
            intent.putExtra(CommonAction.COM_ACT_DATA_1, title);
            intent.putExtra(CommonAction.COM_ACT_DATA_2, progress);
            intent.putExtra(CommonAction.COM_ACT_DATA_3, filePath);
            intent.setClass(this, JumpInstallActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "rocky");
            builder.setContentTitle(title);
            builder.setContentIntent(pendingIntent);
            builder.setSmallIcon(R.mipmap.ic_launcher);
            if (progress >= 0) {
                // 当progress 大于0时才需要显示下载进度
                builder.setContentText(progress + "%");
                builder.setProgress(100, progress, false);
            }
            return builder.build();
        }
    }

}