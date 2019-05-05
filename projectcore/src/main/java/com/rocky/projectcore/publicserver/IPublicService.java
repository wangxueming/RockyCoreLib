package com.rocky.projectcore.publicserver;

import com.alibaba.android.arouter.facade.template.IProvider;
import com.rocky.projectcore.login.IAccountService;

/**
 * 公共数据服务，比如说有个两个fragment不好传值，那怎么破呢。放这里传
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/1/3
 */
public interface IPublicService extends IProvider {
    /**
     * 进行版本下载的跳转，这是是起service
     *
     * @param downloadUrl
     * @param versionName
     */
    void startDownloadApk(String downloadUrl, String versionName);

    /**
     * 是否下载完成
     * @param filePath
     * @return
     */
    boolean isDownloadFinished(String filePath);

    /**
     * 注册监听器
     * @param observer
     */
    void registerObserver(IPublicService.Observer observer);

    /**
     * 注销监听器
     * @param observer
     */
    void unregisterObserver(IPublicService.Observer observer);

    /**
     * 注册监听器
     * @param filePath
     */
    void notifyDownloadStart(String filePath);

    /**
     * 更新下载进度
     * @param downloaded
     * @param total
     */
    void notifyDownloadProgress(float downloaded, long total);

    /**
     * 通知下载失败
     * @param msg
     */
    void notifyDownloadFailure(String msg);

    /**
     * 通知下载成功
     * @param filePath
     */
    void notifyDownloadSuccess(String filePath);

    interface Observer {

        /**
         * 开始下载的回调函数
         */
        void onStart();

        /**
         * 进度更新回调
         * @param downloaded 已下载的数据量
         * @param total 总共需要下载的数据量
         */
        void onProgress(float downloaded, long total);

        /**
         * 下载失败的触发
         * @param msg 失败提示
         */
        void onFailure(String msg);

        /**
         * 下载完成的触发
         */
        void onDone();
    }
}
