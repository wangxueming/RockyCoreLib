package com.rockyw.net.download;

/**
 * 文件下载回调接口
 *
 * @author wangxueming
 * @version 1.0.0
 * @date 2018/12/14
 */
public interface FileDownloadCallback {

    void onStart();

    void onProgress(float downloaded, long total);

    void onFailure(String msg);

    void onDone();
}
