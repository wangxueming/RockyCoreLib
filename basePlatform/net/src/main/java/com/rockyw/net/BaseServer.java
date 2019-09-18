package com.rockyw.net;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;


import com.rockyw.core.util.ActivityUtils;
import com.rockyw.net.BuildConfig;
import com.rockyw.net.converter.GsonConverterFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;

/**
 * 服务器接口基类
 *
 * @author wangxueming
 * @version 1.0.0
 * @date 2018/12/14
 */
public abstract class BaseServer {

    public static final String HEAD_KEY_CUSTOM_BASE_URL = "custom_base_url";

    public static final String LOGOUT_ACTION = "com.rockyw.weather.logout";
    public static final String HEAD_KEY_TOKEN = "Authorization";
    public static final String HEAD_KEY_ACCEPT_LANGUAGE = "Accept-Language";
    public static final String HEAD_KEY_APP_VERSION = "app-version";
    public static final String HEAD_KEY_APP_NAME = "app-name";
    public static final String HEAD_KEY_UG = "User-Agent";
    public static final String HEADER_KEY_AUTHOR_WAY = "author_way";
    /**
     * Authorization: xxx
     */
    public static final String AUTHOR_WAY_TEAM = "author_way_team";

    private static final int DEFAULT_TIMEOUT = 60;

    protected static Context sContext;
    private OkHttpClient mOkHttpClient;
    private Retrofit mRetrofit;
    private MockRetrofit mMockRetrofit;

    /**
     * 注销服务器,发送广播给具体的应用
     */
    public static <T> void logoutServer(T... args) {
        Activity currentActivity = ActivityUtils.getInstance().currentActivity();
        if (sContext != null || currentActivity != null) {
            if (sContext != null) {
                LocalBroadcastManager.getInstance(sContext).sendBroadcast(new Intent(LOGOUT_ACTION));
            } else if (currentActivity != null) {
                LocalBroadcastManager.getInstance(currentActivity).sendBroadcast(new Intent(LOGOUT_ACTION));
            }
        }
    }

    protected <T> T createServerApi(Context context, Class<T> t) {
        sContext = context;

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (BuildConfig.DEBUG) {
            if (sContext == null) {
                mOkHttpClient = new OkHttpClient.Builder()
                        .addInterceptor(new CustomBaseUrlInterceptor())
                        .addInterceptor(initRequestHeader(getRequestHead()))
                        .addInterceptor(logging)
                        .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                        .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                        .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                        .sslSocketFactory(SSLSocketClient.getSSLSocketFactory(), SSLSocketClient.getX509TrustManager())
                        .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
                        .build();
            } else {
                mOkHttpClient = new OkHttpClient.Builder()
                        .cache(initCache(sContext))
                        .addInterceptor(new CustomBaseUrlInterceptor())
                        .addInterceptor(initRequestHeader(getRequestHead()))
                        .addInterceptor(logging)
                        .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                        .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                        .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                        .sslSocketFactory(SSLSocketClient.getSSLSocketFactory(), SSLSocketClient.getX509TrustManager())
                        .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
                        .build();
            }
        } else {
            if (sContext == null) {
                mOkHttpClient = new OkHttpClient.Builder()
                        .addInterceptor(new CustomBaseUrlInterceptor())
                        .addInterceptor(initRequestHeader(getRequestHead()))
                        .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                        .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                        .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                        .sslSocketFactory(SSLSocketClient.getSSLSocketFactory(), SSLSocketClient.getX509TrustManager())
                        .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
                        .build();
            } else {
                mOkHttpClient = new OkHttpClient.Builder()
                        .cache(initCache(sContext))
                        .addInterceptor(new CustomBaseUrlInterceptor())
                        .addInterceptor(initRequestHeader(getRequestHead()))
                        .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                        .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                        .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                        .sslSocketFactory(SSLSocketClient.getSSLSocketFactory(), SSLSocketClient.getX509TrustManager())
                        .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
                        .build();
            }
        }

        mRetrofit = new Retrofit.Builder()
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(getInternalServer())
                .build();

        return mRetrofit.create(t);
    }

    /**
     * 获取Mock后端数据的 Delegate
     *
     * @return BehaviorDelegate
     */
    protected <T> BehaviorDelegate<T> createMockServerApiDelegate(Context context, Class<T> t) {
        sContext = context;

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);


        if (sContext == null) {
            mOkHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new CustomBaseUrlInterceptor())
                    .addInterceptor(initRequestHeader(getRequestHead()))
                    .addInterceptor(logging)
                    .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .sslSocketFactory(SSLSocketClient.getSSLSocketFactory(), SSLSocketClient.getX509TrustManager())
                    .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
                    .build();
        } else {
            mOkHttpClient = new OkHttpClient.Builder()
                    .cache(initCache(sContext))
                    .addInterceptor(new CustomBaseUrlInterceptor())
                    .addInterceptor(initRequestHeader(getRequestHead()))
                    .addInterceptor(logging)
                    .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .sslSocketFactory(SSLSocketClient.getSSLSocketFactory(), SSLSocketClient.getX509TrustManager())
                    .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
                    .build();
        }

        mRetrofit = new Retrofit.Builder()
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(getInternalServer())
                .build();
        mMockRetrofit = new MockRetrofit.Builder(mRetrofit)
                .networkBehavior(NetworkBehavior.create())
                .build();

        return mMockRetrofit.create(t);
    }

    /**
     * 获取服务器URL
     *
     * @return
     */
    protected abstract String getInternalServer();


    /**
     * 获取Http Head
     *
     * @return
     */
    protected abstract HashMap<String, String> getRequestHead();

    /**
     * 请求 Head Interceptor （ 默认请求头）
     *
     * @return
     */
    protected Interceptor initRequestHeader(final HashMap<String, String> heads) {
        Interceptor requestInterceptor = null;

        if (heads == null) {
            return requestInterceptor;
        }

        final String appVersion = "" + heads.get(HEAD_KEY_APP_VERSION);
        final String token = "" + heads.get(HEAD_KEY_TOKEN);
        final String userAgent = "" + heads.get(HEAD_KEY_UG);
        final String appName = "" + heads.get(HEAD_KEY_APP_NAME);
        final String acceptLanguage = "" + heads.get(HEAD_KEY_ACCEPT_LANGUAGE);


        requestInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();
                Request request = chain.request();
                List<String> headerValues = request.headers(HEADER_KEY_AUTHOR_WAY);
                if (headerValues != null && headerValues.size() > 0) {
                    String headerValue = headerValues.get(0);
                    builder.removeHeader(HEADER_KEY_AUTHOR_WAY);
                    if (headerValue.equals(AUTHOR_WAY_TEAM)) {
                        builder.addHeader(HEAD_KEY_TOKEN, token);
                    } else {
                        builder.addHeader(HEAD_KEY_TOKEN, "token " + token);
                    }
                } else {
                    builder.addHeader(HEAD_KEY_TOKEN, "token " + token);
                }

                builder.addHeader(HEAD_KEY_APP_VERSION, appVersion);
                builder.addHeader(HEAD_KEY_UG, userAgent);
                builder.addHeader(HEAD_KEY_APP_NAME, appName);
                builder.addHeader(HEAD_KEY_ACCEPT_LANGUAGE, acceptLanguage);
                Request requestFinal = builder.build();
                Response response = chain.proceed(requestFinal);
                return response;
            }
        };
        return requestInterceptor;
    }


    private Cache initCache(Context context) {
        File httpCacheDirectory = new File(context.getExternalCacheDir(), "responses");
        //设置缓存 10M
        Cache cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);  //10M
        return cache;
    }

    /**
     * 请求时长Interceptor
     *
     * @return
     */
    @Deprecated
    private Interceptor initRequestLog() {
        Interceptor requestInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                long t1 = System.nanoTime();
                Log.d("retrofit", String.format("Sending request %s on %s%n%s", request.url(), chain.connection(), request.headers()));
                Response response = chain.proceed(request);
                long t2 = System.nanoTime();
                Log.d("retrofit", String.format("Received response for %s in %.1fms%n%s", response.request().url(), (t2 - t1) / 1e6d, response.headers()));
                return response;
            }
        };

        return requestInterceptor;
    }

    /**
     * 反射方法获取app的BulidConfig的DEBUG值
     *
     * @param context
     * @return
     */
    @Deprecated
    private boolean getBuildDebug(Context context) {
        try {
            Class<?> clazz = Class.forName(context.getPackageName() + ".BuildConfig");
            Field field = clazz.getField("DEBUG");
            return (boolean) field.get(null);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

}
