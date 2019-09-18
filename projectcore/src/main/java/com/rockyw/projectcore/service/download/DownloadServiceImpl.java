package com.rockyw.projectcore.service.download;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rockyw.projectcore.common.router.CommonAction;
import com.rockyw.projectcore.common.router.RouterUrl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/1/23
 */
@Route(path = RouterUrl.SERVER_PUBLIC_SERVICE, name = "公共的Service获取器")
public class DownloadServiceImpl implements IDownloadService {
    private Context mContext;

    private final List<IDownloadService.Observer> mObservers = new ArrayList<>();
    private Object mUserLock = new Object();

    private boolean isDownloadFinished = false;
    private String mLastDownloadPath;

    @Override
    public void init(Context context) {
        mContext = context;
    }

    @Override
    public void startDownloadApk(String downloadUrl, String versionName) {
        Intent intent = new Intent();
        intent.setClass(mContext, DownloadIntentService.class);
        intent.putExtra(CommonAction.COM_ACT_DATA_1, downloadUrl);
        intent.putExtra(CommonAction.COM_ACT_DATA_2, versionName);
        mContext.startService(intent);
    }

    @Override
    public boolean isDownloadFinished(String filePath) {
        if (TextUtils.equals(filePath, mLastDownloadPath) && TextUtils.isEmpty(filePath)) {
            return isDownloadFinished;
        }
        return false;
    }

    @Override
    public void registerObserver(IDownloadService.Observer observer) {
        if (observer != null && !mObservers.contains(observer)) {
            mObservers.add(observer);
        }
    }

    @Override
    public void unregisterObserver(IDownloadService.Observer observer) {
        if (observer != null) {
            mObservers.remove(observer);
        }
    }

    @Override
    public void notifyDownloadStart(String filePath) {
        isDownloadFinished = false;
        mLastDownloadPath = filePath;
        IDownloadService.Observer[] observers = getObservers();
        for (int i = observers.length - 1; i >= 0; --i) {
            observers[i].onStart();
        }
    }

    @Override
    public void notifyDownloadProgress(float downloaded, long total) {
        isDownloadFinished = false;
        IDownloadService.Observer[] observers = getObservers();
        for (int i = observers.length - 1; i >= 0; --i) {
            observers[i].onProgress(downloaded, total);
        }
    }

    @Override
    public void notifyDownloadFailure(String msg) {
        isDownloadFinished = false;
        IDownloadService.Observer[] observers = getObservers();
        for (int i = observers.length - 1; i >= 0; --i) {
            observers[i].onFailure(msg);
        }
    }

    @Override
    public void notifyDownloadSuccess(String filePath) {
        synchronized (mUserLock) {
            isDownloadFinished = true;
            mLastDownloadPath = filePath;
            IDownloadService.Observer[] observers = getObservers();
            for (int i = observers.length - 1; i >= 0; --i) {
                observers[i].onDone();
            }
        }
    }

    @NonNull
    private IDownloadService.Observer[] getObservers() {
        return mObservers.toArray(new IDownloadService.Observer[mObservers.size()]);
    }
}
