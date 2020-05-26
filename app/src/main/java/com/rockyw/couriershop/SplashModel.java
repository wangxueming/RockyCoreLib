package com.rockyw.couriershop;

import com.rockyw.projectcore.common.bean.VersionResponse;
import com.rockyw.couriershop.net.Server;

import io.reactivex.Observable;

/**
 * @author: wangxueming
 * @version: 1.0.0
 * @date: 2018/12/20
 */
public class SplashModel implements ISplashContract.Model {

    @Override
    public Observable<VersionResponse> getLatestVersion(String packageName, String currentVersionNumber, int platform, String channel) {
        return Server.getServerApi().getLastestVersion(packageName, currentVersionNumber, platform, channel);
    }
}