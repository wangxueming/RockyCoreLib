package com.rocky.locate;

/**
 * 用于获取定位信息请求的回调
 *
 * @author: wangxueming
 * @version: 1.0.0
 * @date: 2018/12/14
 */
public interface ILocateCallback {
    /**
     * 获取定位成功
     */
    void onSuccess();

    /**
     * 获取定位失败
     */
    void onFailed();
}
