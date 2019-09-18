package com.rockyw.net.download;

import com.rockyw.net.CustomBaseUrlInterceptor;
import com.rockyw.net.SSLSocketClient;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.internal.Util;

/**
 *
 *
 * @author wangxueming
 * @version 1.0.0
 * @date 2018/12/14
 */
public class OkHttpManager {

    private static final List<Protocol> DEFAULT_PROTOCOLS = Util.immutableList(
            Protocol.HTTP_2, Protocol.SPDY_3, Protocol.HTTP_1_1);

    private static final List<ConnectionSpec> DEFAULT_CONNECTION_SPECS = Util.immutableList(
            ConnectionSpec.MODERN_TLS, ConnectionSpec.COMPATIBLE_TLS, ConnectionSpec.CLEARTEXT);

    private static OkHttpManager sOkHttpManager;
    private OkHttpClient okHttpClient;

    private OkHttpManager() {

    }

    public static OkHttpManager getInstance() {
        if (sOkHttpManager == null) {
            sOkHttpManager = new OkHttpManager();
        }
        return sOkHttpManager;
    }


    public OkHttpClient getDefaultOkHttpClient() {

        if (okHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .addInterceptor(new CustomBaseUrlInterceptor())
                    .connectTimeout(10000, TimeUnit.SECONDS)
                    .readTimeout(10000, TimeUnit.SECONDS)
                    .writeTimeout(10000, TimeUnit.SECONDS)
                    .sslSocketFactory(SSLSocketClient.getSSLSocketFactory(), SSLSocketClient.getX509TrustManager())
                    .hostnameVerifier(SSLSocketClient.getHostnameVerifier());

            okHttpClient = builder.build();
        }

        return okHttpClient;

    }


    public static void download(String url, File target, FileDownloadCallback callback) {
        if (url != null && url.length() > 0 && target != null) {
            FileDownloadTask task = new FileDownloadTask(url, target, callback);
            task.execute();
        }
    }


}
