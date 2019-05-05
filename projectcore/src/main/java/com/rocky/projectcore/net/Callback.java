package com.rocky.projectcore.net;

import android.net.ParseException;
import android.text.TextUtils;

import com.google.gson.JsonParseException;
import com.rocky.net.BaseServer;

import org.json.JSONException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import retrofit2.Call;
import retrofit2.HttpException;
import retrofit2.Response;

/**
 * 基本网络接口
 *
 * @author wangxueming
 * @version 1.0.0
 * @date 2018/12/14
 */
public abstract class Callback<T> implements retrofit2.Callback<T> {

    public static final String SUCCESS = "0000";
    public static final String WINFAE_UNAUTHORIZED = "4006";
    public static final String PARAMS_ERROR = "9001";
    public static final String NO_NETWORK = "19999";

    /**
     * 对应HTTP的状态码
     */
    protected static final int NO_CONTENT = 204;
    protected static final int BAD_REQUEST = 400;
    protected static final int UNAUTHORIZED = 401;
    protected static final int FORBIDDEN = 403;
    protected static final int NOT_FOUND = 404;
    protected static final int REQUEST_TIMEOUT = 408;
    protected static final int CONFLICT = 409;
    protected static final int INTERNAL_SERVER_ERROR = 500;
    protected static final int BAD_GATEWAY = 502;
    protected static final int SERVICE_UNAVAILABLE = 503;
    protected static final int GATEWAY_TIMEOUT = 504;

    protected static final int PARSE_EXCEPTION = 800;
    protected static final int TIME_OUT_EXCEPTION = 801;
    protected static final int CONNECT_EXCEPTION = 802;
    protected static final int UNKNOWN_HOST_EXCEPTION = 803;

    protected static final int OTHER_EXCEPTION = 900;

    /**
     * 通用成功返回接口
     *
     * @param t
     */
    public abstract void onSuccess(T t);

    /**
     * 通用失败返回接口
     *
     * @param message
     */
    public abstract void onFailure(String message);

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        //401,表示用户token不合法，进行登出操作
        if (response.code() == UNAUTHORIZED) {
            BaseServer.logoutServer();
            onFailure(response.message());
        } else if (response.code() >= BAD_REQUEST) {
            //400以上的其他错误直接返回错误消息内容
            onFailure(response.message());
        } else {
            if (response.body() instanceof BaseResponse) {
                BaseResponse r = (BaseResponse) response.body();
                if (TextUtils.equals(r.getCode(), WINFAE_UNAUTHORIZED)) {
                    BaseServer.logoutServer();
                    onFailure(r.getMessage());
                } else if (TextUtils.equals(r.getCode(), SUCCESS)) {
                    onSuccess(response.body());
                } else {
                    onFailure(r.getMessage());
                }
            } else {
                onSuccess(response.body());
            }
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable e) {
        String ex = "";
        int code = 0;
        if (e instanceof HttpException) {
            //HTTP错误
            HttpException httpException = (HttpException) e;
            switch (httpException.code()) {
                case UNAUTHORIZED:
                    BaseServer.logoutServer();
                    break;
                case CONFLICT:
                    try {
                        ex = httpException.response().errorBody().string();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    break;
                case FORBIDDEN:
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    //均视为网络错误
                    ex = httpException.code() + " " + "Network error";
                    break;
            }
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            //均视为解析错误
            ex = "error_parse_data";
            code = PARSE_EXCEPTION;
        } else if (e instanceof SocketTimeoutException
                || e instanceof TimeoutException) {
            ex = "Network connection timed out, please check your network status.";
            code = TIME_OUT_EXCEPTION;
        } else if (e instanceof ConnectException) {
            ex = "Network anomaly, please check your network status.";
            code = CONNECT_EXCEPTION;
        } else if (e instanceof UnknownHostException) {
            code = UNKNOWN_HOST_EXCEPTION;
            ex = "Network anomaly, please check your network status.";
        } else if (e instanceof NullPointerException) {
            // 应该是HTTP状态码为204 no content
            ex = "";
            code = NO_CONTENT;
        } else if (e instanceof IOException) {
            ex = "Network connection timed out, please check your network status.";
            code = TIME_OUT_EXCEPTION;
        } else {
            //未知错误或服务器返回
            ex = e.getMessage();
            code = OTHER_EXCEPTION;
        }
        e.printStackTrace();
        onFailure(ex);
    }


    /**
     * 判断网络请求是否超过生命周期
     *
     * @return
     */
    protected boolean isRequestLifeOver(Object context) {
        if (context == null) {
            return true;
        }
        return isActivityLifeOver(context) || isFragmentLifeOver(context);
    }


    /**
     * Activity生命周期是否结束
     *
     * @param context
     * @return
     */
    private boolean isActivityLifeOver(Object context) {
        if (context instanceof android.app.Activity) {
            android.app.Activity activity = (android.app.Activity) context;
            if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.JELLY_BEAN_MR1
                    && activity.isDestroyed()) {
                return true;
            }
            if (activity.isFinishing()) {
                return true;
            }
        }
        return false;
    }


    /**
     * Fragment生命周期是否结束
     *
     * @param context
     * @return
     */
    @SuppressWarnings("AliDeprecation")
    private boolean isFragmentLifeOver(Object context) {
        if (context instanceof android.app.Fragment) {
            android.app.Fragment fragment = (android.app.Fragment) context;
            if (fragment.isRemoving() || fragment.isDetached()) {
                return true;
            }
        }

        if (context instanceof android.support.v4.app.Fragment) {
            android.support.v4.app.Fragment fragment = (android.support.v4.app.Fragment) context;
            if (fragment.isRemoving() || fragment.isDetached()) {
                return true;
            }
        }

        return false;
    }
}