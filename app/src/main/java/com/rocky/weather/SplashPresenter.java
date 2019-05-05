package com.rocky.weather;

import com.rocky.core.base.mvp.BasePresenter;
import com.rocky.core.util.AppUtils;
import com.rocky.projectcore.common.bean.VersionResponse;
import com.rocky.projectcore.UrlServiceManager;
import com.rocky.projectcore.config.AppConfig;
import com.rocky.projectcore.net.CommonObserver;
import com.rocky.projectcore.publicdata.IPublicDataService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author: wangxueming
 * @version: 1.0.0
 * @date: 2018/12/20
 */
public class SplashPresenter extends BasePresenter<ISplashContract.View, ISplashContract.Model> implements ISplashContract.Presenter {
    @Override
    protected ISplashContract.Model createModel() {
        return new SplashModel();
    }

    @Override
    public void checkVersion() {
        addDisposable(getModel().getLatestVersion(AppUtils.getAppPackageName(), AppUtils.getVerName(getContext()), AppConfig.PLATFORM_ANDROID, AppConfig.CHANNEL_OFFICE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new CommonObserver<VersionResponse>(getView(), true) {
                    @Override
                    public void onSuccess(VersionResponse response) {
                        IPublicDataService accountService = UrlServiceManager.getPublicDataService();
                        accountService.setLatestVersion(response.data.versionNumber);
                        getView().handleVersionInfo(response);
                    }
                    @Override
                    public void onFail(String code, String msg){
                        super.onFail(code, msg);
                        getView().obtainVersionInfoFailed(code, msg);
                    }
                }));

    }
}
