package com.rockyw.net.v2;

import com.google.gson.Gson;
import com.rockyw.net.v2.function.FilterUselessFieldFunction;
import com.rockyw.net.v2.function.GenericFunction;
import com.rockyw.net.v2.interceptor.HeaderInterceptor;
import com.rockyw.net.v2.interceptor.HttpLoggingInterceptor;
import com.rockyw.net.SSLSocketClient;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 *
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/2/18
 */
public class Server {

    /**
     * 网络延迟时间（单位毫秒）
     */
    public static final int DELAY_TIME = 0;

    public static final Map<String, Object> EMPTY_PARAM = new HashMap<>();
    public static final Map<String, Object> EMPTY_BODY = null;

    public static final String HEADER_KEY_AUTHOR_WAY = "author_way";
    /**
     * Authorization: token xxx
     */
    public static final String AUTHOR_WAY_NORMAL = "author_way_normal";
    /**
     * Authorization: xxx
     */
    public static final String AUTHOR_WAY_TEAM = "author_way_team";

    public static final String HEAD_KEY_TOKEN = "Authorization";
    public static final String HEAD_KEY_ACCEPT_LANGUAGE = "Accept-Language";
    public static final String HEAD_KEY_APP_VERSION = "app-version";
    public static final String HEAD_KEY_UG = "User-Agent";
    private static final int DEFAULT_TIMEOUT = 60;  // seconds

    private OkHttpClient mOkHttpClient;
    private Retrofit mRetrofit;
    private ApiService mApiService;

    private static Gson sGson = new Gson();

    private Server() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        mOkHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new HeaderInterceptor())
                .addInterceptor(logging)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .sslSocketFactory(SSLSocketClient.getSSLSocketFactory(), SSLSocketClient.getX509TrustManager())
                .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
                .build();

        mRetrofit = new Retrofit.Builder()
                .client(mOkHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(getServer())
                .build();
        mApiService = mRetrofit.create(ApiService.class);
    }

    public void resetBaseUrl() {
        mRetrofit = mRetrofit.newBuilder()
                .baseUrl(getServer())
                .build();
        mApiService = mRetrofit.create(ApiService.class);
    }


    private String getServer() {
        return "";
    }

    /**
     * 通用的get方法
     *
     * @param url
     * @param parameters
     * @param observer   尽量用 {@link com.rockyw.net.v2.observer.BaseObserver} ，不然就需要自己去处理因为RxJava带来的内存泄漏的隐患
     * @param <T>
     */
    public <T> void get(final String url, Map<String, Object> parameters, final Observer<T> observer) {
        mApiService
                .get(url, parameters)
                .delay(DELAY_TIME, TimeUnit.MILLISECONDS)
                .map(new FilterUselessFieldFunction())
                .map(new GenericFunction<T>(observer))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 通用的get方法
     *
     * @param url
     * @param parameters
     * @param observer   尽量用 {@link com.rockyw.net.v2.observer.BaseObserver} ，不然就需要自己去处理因为RxJava带来的内存泄漏的隐患
     * @param <T>
     */
    public <T> void get2(final String url, Map<String, Object> parameters, final Observer<T> observer) {
        mApiService
                .get2(url, parameters)
                .delay(DELAY_TIME, TimeUnit.MILLISECONDS)
                .map(new FilterUselessFieldFunction())
                .map(new GenericFunction<T>(observer))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 通用动态的get方法（不使用 BASE_URL）
     *
     * @param url
     * @param observer 尽量用 {@link com.rockyw.net.v2.observer.BaseObserver} ，不然就需要自己去处理因为RxJava带来的内存泄漏的隐患
     * @param <T>
     */
    public <T> void get3(final String url, final Observer<T> observer) {
        mApiService
                .get3(url)
                .delay(DELAY_TIME, TimeUnit.MILLISECONDS)
                .map(new FilterUselessFieldFunction())
                .map(new GenericFunction<T>(observer))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 通用的get方法（未指定线程，需调用者自己控制）
     *
     * @param url
     * @param parameters
     * @param <T>
     */
    public <T> Observable<T> get(final String url, Map<String, Object> parameters, final Class<T> clz) {
        return mApiService
                .get(url, parameters)
                .delay(DELAY_TIME, TimeUnit.MILLISECONDS)
                .map(new FilterUselessFieldFunction())
                .map(new Function<String, T>() {
                    @Override
                    public T apply(@NonNull String s) throws Exception {
                        return sGson.fromJson(s, clz);
                    }
                });
    }

    /**
     * 通用的get方法（未指定线程，需调用者自己控制）
     *
     * @param url
     * @param parameters
     * @param <T>
     */
    public <T> Observable<T> get2(final String url, Map<String, Object> parameters, final Class<T> clz) {
        return mApiService
                .get2(url, parameters)
                .delay(DELAY_TIME, TimeUnit.MILLISECONDS)
                .map(new FilterUselessFieldFunction())
                .map(new Function<String, T>() {
                    @Override
                    public T apply(@NonNull String s) throws Exception {
                        return sGson.fromJson(s, clz);
                    }
                });
    }

    /**
     * 通用的post方法
     *
     * @param url
     * @param request
     * @param observer
     * @param <T>
     */
    public <T> void post(final String url, Object request, final Observer<T> observer) {
        RequestBody body = convertRequestBody(request);
        mApiService
                .post(url, body)
                .delay(DELAY_TIME, TimeUnit.MILLISECONDS)
                .map(new FilterUselessFieldFunction())
                .map(new GenericFunction<T>(observer))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 通用的post方法（未指定线程，需调用者自己控制）
     *
     * @param url
     * @param request
     * @param <T>
     */
    public <T> Observable<T> post(final String url, Object request, final Class<T> clz) {
        RequestBody body = convertRequestBody(request);
        return mApiService
                .post(url, body)
                .delay(DELAY_TIME, TimeUnit.MILLISECONDS)
                .map(new FilterUselessFieldFunction())
                .map(new Function<String, T>() {
                    @Override
                    public T apply(@NonNull String s) throws Exception {
                        return sGson.fromJson(s, clz);
                    }
                });
    }

    /**
     * 通用的post方法（未指定线程，需调用者自己控制）
     *
     * @param url
     * @param request
     * @param <T>
     */
    public <T> Observable<T> post2(final String url, Object request, final Class<T> clz) {
        RequestBody body = convertRequestBody(request);
        return mApiService
                .post2(url, body)
                .delay(DELAY_TIME, TimeUnit.MILLISECONDS)
                .map(new FilterUselessFieldFunction())
                .map(new Function<String, T>() {
                    @Override
                    public T apply(@NonNull String s) throws Exception {
                        return sGson.fromJson(s, clz);
                    }
                });
    }

    /**
     * 通用的post方法
     *
     * @param url
     * @param request
     * @param observer
     * @param <T>
     */
    public <T> void post2(final String url, Object request, final Observer<T> observer) {
        RequestBody body = convertRequestBody(request);
        mApiService
                .post2(url, body)
                .delay(DELAY_TIME, TimeUnit.MILLISECONDS)
                .map(new FilterUselessFieldFunction())
                .map(new GenericFunction<T>(observer))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 通用的patch方法
     *
     * @param url
     * @param request
     * @param observer
     * @param <T>
     */
    public <T> void patch(final String url, Object request, final Observer<T> observer) {
        RequestBody body = convertRequestBody(request);
        mApiService
                .post(url, body)
                .delay(DELAY_TIME, TimeUnit.MILLISECONDS)
                .map(new FilterUselessFieldFunction())
                .map(new GenericFunction<T>(observer))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 通用的patch方法
     *
     * @param url
     * @param request
     * @param observer
     * @param <T>
     */
    public <T> void patch2(final String url, Object request, final Observer<T> observer) {
        RequestBody body = convertRequestBody(request);
        mApiService
                .patch2(url, body)
                .delay(DELAY_TIME, TimeUnit.MILLISECONDS)
                .map(new FilterUselessFieldFunction())
                .map(new GenericFunction<T>(observer))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 通用的patch方法（未指定线程，需调用者自己控制）
     *
     * @param url
     * @param request
     * @param <T>
     */
    public <T> Observable<T> patch2(final String url, Object request, final Class<T> clz) {
        RequestBody body = convertRequestBody(request);
        return mApiService
                .patch2(url, body)
                .delay(DELAY_TIME, TimeUnit.MILLISECONDS)
                .map(new FilterUselessFieldFunction())
                .map(new Function<String, T>() {
                    @Override
                    public T apply(@NonNull String s) throws Exception {
                        return sGson.fromJson(s, clz);
                    }
                });
    }


    /**
     * 通用的put方法
     *
     * @param url
     * @param request
     * @param observer
     * @param <T>
     */
    public <T> void put(final String url, Object request, final Observer<T> observer) {
        RequestBody body = convertRequestBody(request);
        mApiService
                .put(url, body)
                .delay(DELAY_TIME, TimeUnit.MILLISECONDS)
                .map(new FilterUselessFieldFunction())
                .map(new GenericFunction<T>(observer))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 通用的put方法
     *
     * @param url
     * @param request
     * @param observer
     * @param <T>
     */
    public <T> void put2(final String url, Object request, final Observer<T> observer) {
        RequestBody body = convertRequestBody(request);
        mApiService
                .put2(url, body)
                .delay(DELAY_TIME, TimeUnit.MILLISECONDS)
                .map(new FilterUselessFieldFunction())
                .map(new GenericFunction<T>(observer))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 通用的delete方法
     *
     * @param url
     * @param parameters
     * @param observer   尽量用 {@link com.rockyw.net.v2.observer.BaseObserver} ，不然就需要自己去处理因为RxJava带来的内存泄漏的隐患
     * @param <T>
     */
    public <T> void delete(final String url, Map<String, Object> parameters, final Observer<T> observer) {
        mApiService
                .delete(url, parameters)
                .delay(DELAY_TIME, TimeUnit.MILLISECONDS)
                .map(new FilterUselessFieldFunction())
                .map(new GenericFunction<T>(observer))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 通用的delete方法
     *
     * @param url
     * @param parameters
     * @param observer   尽量用 {@link com.rockyw.net.v2.observer.BaseObserver} ，不然就需要自己去处理因为RxJava带来的内存泄漏的隐患
     * @param <T>
     */
    public <T> void delete2(final String url, Map<String, Object> parameters, final Observer<T> observer) {
        mApiService
                .delete2(url, parameters)
                .delay(DELAY_TIME, TimeUnit.MILLISECONDS)
                .map(new FilterUselessFieldFunction())
                .map(new GenericFunction<T>(observer))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public Observable<String> uploadFileToQiqiu(final File file) {
        return uploadFileToQiqiu(file, null);
    }

    /**
     * 上传文件到第三方存储服务器获取文件资源链接
     */
    public Observable<String> uploadFileToQiqiu(final File file, final String suffix) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(final ObservableEmitter<String> observableEmitter) throws Exception {
                // 七牛没有提供同步的方法，所以这里用的回调
//                QiniuUploadUtil.upLoadFile(file, suffix, new QiniuUploadListener() {
//                    @Override
//                    public void onSuccess(String key) {
//                        observableEmitter.onNext(key);
//                        observableEmitter.onComplete();
//                    }
//
//                    @Override
//                    public void onFailure() {
//                        observableEmitter.onError(new IOException());
//                        observableEmitter.onComplete();
//                    }
//                });
            }
        }).observeOn(Schedulers.io());
    }

    /**
     * 通用的delete带body方法
     *
     * @param url
     * @param observer 尽量用 {@link com.rockyw.net.v2.observer.BaseObserver} ，不然就需要自己去处理因为RxJava带来的内存泄漏的隐患
     * @param <T>
     */
    public <T> void deleteWithBody(final String url, Object request, final Observer<T> observer) {
        RequestBody body = convertRequestBody(request);
        mApiService
                .deleteWithBody(url, body)
                .delay(DELAY_TIME, TimeUnit.MILLISECONDS)
                .map(new FilterUselessFieldFunction())
                .map(new GenericFunction<T>(observer))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @android.support.annotation.NonNull
    private RequestBody convertRequestBody(Object request) {
        String json = sGson.toJson(request);
        return RequestBody.create(MediaType.parse("application/x-www-form-urlencoded; charset=utf-8"), json);
    }

    private static class SingletonHolder {
        private static Server INSTANCE = new Server();
    }

    public static Server getInstance() {
        return SingletonHolder.INSTANCE;
    }

}
