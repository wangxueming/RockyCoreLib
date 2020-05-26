package com.rockyw.couriershop;

import com.rockyw.core.base.mvp.inter.IModel;
import com.rockyw.core.base.mvp.inter.IPresenter;
import com.rockyw.core.base.mvp.inter.IView;
import com.rockyw.projectcore.common.bean.VersionResponse;

import io.reactivex.Observable;

/**
 * @author: wangxueming
 * @version: 1.0.0
 * @date: 2018/12/20
 */
public interface ISplashContract {
    interface View extends IView {
        /**
         * 处理版本信息
         * @param response
         */
        void handleVersionInfo(VersionResponse response);

        /**
         * 获取版本信息失败
         * @param code
         * @param msg
         */
        void obtainVersionInfoFailed(String code, String msg);
    }

    interface Presenter extends IPresenter<View> {
        /**
         * 转交版本信息网络请求
         */
        void checkVersion();
    }

    interface Model extends IModel {
        /**
         * 获取最新版本的版本信息
         * @param packageName
         * @param currentVersionNumber
         * @param platform
         * @param channel
         * @return
         */
        Observable<VersionResponse> getLatestVersion(String packageName, String currentVersionNumber, int platform, String channel);
    }
}
