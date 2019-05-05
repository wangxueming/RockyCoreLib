package com.rocky.projectcore.publicserver;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rocky.projectcore.common.router.CommonAction;
import com.rocky.projectcore.common.router.RouterUrl;
import com.rocky.projectcore.service.DownloadService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/1/23
 */
@Route(path = RouterUrl.SERVER_PUBLIC_SERVICE, name = "公共的Service获取器")
public class PublicServiceImpl implements IPublicService {
    private Context mContext;

    private final List<IPublicService.Observer> mObservers = new ArrayList<>();
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
        intent.setClass(mContext, DownloadService.class);
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
    public void registerObserver(IPublicService.Observer observer) {
        if (observer != null && !mObservers.contains(observer)) {
            mObservers.add(observer);
        }
    }

    @Override
    public void unregisterObserver(IPublicService.Observer observer) {
        if (observer != null) {
            mObservers.remove(observer);
        }
    }

    @Override
    public void notifyDownloadStart(String filePath) {
        isDownloadFinished = false;
        mLastDownloadPath = filePath;
        IPublicService.Observer[] observers = getObservers();
        for (int i = observers.length - 1; i >= 0; --i) {
            observers[i].onStart();
        }
    }

    @Override
    public void notifyDownloadProgress(float downloaded, long total) {
        isDownloadFinished = false;
        IPublicService.Observer[] observers = getObservers();
        for (int i = observers.length - 1; i >= 0; --i) {
            observers[i].onProgress(downloaded, total);
        }
    }

    @Override
    public void notifyDownloadFailure(String msg) {
        isDownloadFinished = false;
        IPublicService.Observer[] observers = getObservers();
        for (int i = observers.length - 1; i >= 0; --i) {
            observers[i].onFailure(msg);
        }
    }

    @Override
    public void notifyDownloadSuccess(String filePath) {
        synchronized (mUserLock) {
            isDownloadFinished = true;
            mLastDownloadPath = filePath;
            IPublicService.Observer[] observers = getObservers();
            for (int i = observers.length - 1; i >= 0; --i) {
                observers[i].onDone();
            }
        }
    }

    @NonNull
    private IPublicService.Observer[] getObservers() {
        return mObservers.toArray(new IPublicService.Observer[mObservers.size()]);
    }
}
