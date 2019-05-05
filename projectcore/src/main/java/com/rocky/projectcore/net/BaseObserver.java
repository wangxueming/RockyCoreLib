package com.rocky.projectcore.net;

import android.app.Activity;
import android.content.Context;
import android.net.ParseException;
import android.text.TextUtils;

import com.google.gson.JsonParseException;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.rocky.projectcore.common.bean.Merge2Response;
import com.rocky.core.base.mvp.inter.IView;
import com.rocky.core.util.ActivityUtils;
import com.rocky.core.util.NetUtils;
import com.rocky.core.util.Tip;
import com.rocky.core.util.logger.L;
import com.rocky.net.BaseServer;
import com.rocky.net.R;
import com.rocky.projectcore.common.bean.Merge3Response;

import org.json.JSONException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import io.reactivex.observers.ResourceObserver;
import retrofit2.HttpException;

/**
 * 处理网络请求的技术类
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/1/4
 */
public abstract class BaseObserver<T> extends ResourceObserver<T> {

    /**
     * 逻辑上的code
     */
    public static final String SUCCESS = "0000";
    public static final String UNKNOWN_ERROR = "9999";
    public static final String PARAMS_ERROR = "9001";
    public static final String WINFAE_UNAUTHORIZED = "4006";
    public static final String NO_NETWORK = "19999";
    public static final int EXCEPTION = 0;
    public static final int FAIL = -1;

    /**
     * 对应HTTP的状态码
     */
    protected static final String NO_CONTENT = "204";
    protected static final int UNAUTHORIZED = 401;
    protected static final int FORBIDDEN = 403;
    protected static final int NOT_FOUND = 404;
    protected static final int REQUEST_TIMEOUT = 408;
    protected static final int CONFLICT = 409;
    protected static final int INTERNAL_SERVER_ERROR = 500;
    protected static final int BAD_GATEWAY = 502;
    protected static final int SERVICE_UNAVAILABLE = 503;
    protected static final int GATEWAY_TIMEOUT = 504;

    protected static final String PARSE_EXCEPTION = "800";
    protected static final String TIME_OUT_EXCEPTION = "801";
    protected static final String CONNECT_EXCEPTION = "802";
    protected static final String UNKNOWN_HOST_EXCEPTION = NO_NETWORK;


    protected Object tag;
    protected Context mContext;

    protected IView mView;
    protected String mErrorMsg;
    protected String msg;
    protected IView mDialogView = null;
    protected SmartRefreshLayout rlRefreshLayout = null;

    private BaseObserver() {
    }

    public BaseObserver(Object tag) {
        this.tag = tag;
        if (tag instanceof Context) {
            mContext = (Context) tag;
        }
    }

    public BaseObserver(IView view) {
        this.mView = view;
    }

    protected BaseObserver(IView view, boolean isShowHUD) {
        this.mView = view;
        if (isShowHUD) {
            this.mDialogView = view;
        }
    }

    protected BaseObserver(IView view, SmartRefreshLayout rlRefresh) {
        this.mView = view;
        this.rlRefreshLayout = rlRefresh;
    }

    protected BaseObserver(IView view, String msg1) {
        mView = view;
        mDialogView = view;
        msg = msg1;
    }

    @Override
    public void onStart() {
//        Activity currentActivity = ActivityUtils.getInstance().currentActivity();
//        if (currentActivity != null && !NetUtils.isNetConnected(currentActivity)) {
//            Tip.onNotice("啊哦，网络开小差了~");
            //#为什么这里注释掉呢。因为onFail在onError中以UnknownHostException的形式出现
//            onFail(NO_NETWORK, "啊哦，网络开小差了~");
//        }
    }

    @Override
    public void onNext(T t) {
        if (t != null) {
            if (t instanceof BaseResponse) {
                handleSingleResponse(t);
            } else if (t instanceof Merge2Response) {
                handleMerge2(t);
            } else if (t instanceof Merge3Response) {
                handleMerge3(t);
            } else {
                //#这种情况本不应该发生
                onSuccess(t);
            }
        } else {
            onFail(UNKNOWN_ERROR, getWFString(R.string.empty_data_from_server));
        }
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        String ex = "";
        String codeMsg = "";
        if (e instanceof HttpException) {
            //HTTP错误
            HttpException httpException = (HttpException) e;
            L.e("onError: HttpException " + httpException.message() + "---" + httpException.code());
            int code = httpException.code();
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
                    ex = httpException.code() + " " + getWFString(R.string.bad_network);
                    break;
            }
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            //均视为解析错误
            ex = getWFString(R.string.error_parse_data);
            codeMsg = PARSE_EXCEPTION;
            L.e("onError: Parse " + ex + " " + e.toString());
        } else if (e instanceof SocketTimeoutException
                || e instanceof TimeoutException) {
            ex = getWFString(R.string.network_time_out);
            codeMsg = TIME_OUT_EXCEPTION;
            L.e("onError: SocketTimeoutException " + ex + " " + e.toString());
        } else if (e instanceof ConnectException) {
            ex = getWFString(R.string.network_anomaly);
            codeMsg = CONNECT_EXCEPTION;
            L.e("onError: ConnectException " + ex + " " + e.toString());
        } else if (e instanceof UnknownHostException) {
            codeMsg = UNKNOWN_HOST_EXCEPTION;
            ex = getWFString(R.string.network_anomaly);
            L.e("onError: UnknownHostException " + ex + " " + e.toString());
        } else if (e instanceof NullPointerException) {
            // 应该是HTTP状态码为204 no content
            ex = "";
            codeMsg = NO_CONTENT;
        } else if (e instanceof IOException) {
            ex = getWFString(R.string.network_time_out);
            codeMsg = TIME_OUT_EXCEPTION;
        } else {
            //未知错误或服务器返回
            ex = e.getMessage();
            codeMsg = UNKNOWN_ERROR;
            e.printStackTrace();
            L.e("onError: OtherException " + ex + " " + e.toString());
        }
        onFail(codeMsg, ex);
        onEnd();
    }

    @Override
    public void onComplete() {
        onEnd();
    }

    /**
     * 调用开始
     */
//    public abstract void onStart();

    /**
     * 返回成功
     *
     * @param t
     */
    public abstract void onSuccess(T t);

    /**
     * 返回失败
     *
     * @param code
     * @param error
     */
    public abstract void onFail(String code, String error);

    /**
     * 调用结束
     * 因为onComplete 和 onError 是互斥的，所以在两个方法结束之后都需要调用onEnd()
     */
    public abstract void onEnd();

    private String getWFString(int stringId) {
        if (mContext == null) {
            mContext = ActivityUtils.getInstance().currentActivity().getApplicationContext();
        }
        if (mContext == null) {
            return "no_context";
        }
        return mContext.getString(stringId);
    }

    private void handleSingleResponse(T t) {
        //#这是单一的返回值
        BaseResponse response = (BaseResponse) t;
        if (TextUtils.equals(response.getCode(), SUCCESS) || TextUtils.equals(response.getCode(), PARAMS_ERROR)) {
            onSuccess(t);
        } else if (TextUtils.equals(response.getCode(), WINFAE_UNAUTHORIZED)) {
            BaseServer.logoutServer();
            onFail(response.getCode(), response.getMessage());
        } else {
            onFail(response.getCode(), response.getMessage());
        }
    }
    private void handleMerge2(T t) {
        //#这是两个返回值一起合并后的返回值
        Merge2Response response = (Merge2Response) t;
        BaseResponse inner1Response = response.getResponse1();
        BaseResponse inner2Response = response.getResponse2();
        boolean inner1Success = (inner1Response != null) && (TextUtils.equals(inner1Response.getCode(), SUCCESS) || TextUtils.equals(inner1Response.getCode(), PARAMS_ERROR));
        boolean inner2Success = (inner2Response != null) && (TextUtils.equals(inner2Response.getCode(), SUCCESS) || TextUtils.equals(inner2Response.getCode(), PARAMS_ERROR));
        if (inner1Success && inner2Success) {
            onSuccess(t);
        } else {
            if (!inner1Success && inner1Response != null) {
                if (TextUtils.equals(inner1Response.getCode(), WINFAE_UNAUTHORIZED)) {
                    BaseServer.logoutServer();
                    onFail(inner1Response.getCode(), inner1Response.getMessage());
                } else {
                    onFail(inner1Response.getCode(), inner1Response.getMessage());
                }
            } else if (!inner2Success && inner2Response != null) {
                if (TextUtils.equals(inner2Response.getCode(), WINFAE_UNAUTHORIZED)) {
                    BaseServer.logoutServer();
                    onFail(inner2Response.getCode(), inner2Response.getMessage());
                } else {
                    onFail(inner2Response.getCode(), inner2Response.getMessage());
                }
            } else {
                onFail(UNKNOWN_ERROR, getWFString(R.string.empty_data_from_server));
            }
        }
    }
    private void handleMerge3(T t) {
        //#这是两个返回值一起合并后的返回值
        Merge3Response response = (Merge3Response) t;
        BaseResponse inner1Response = response.getResponse1();
        BaseResponse inner2Response = response.getResponse2();
        BaseResponse inner3Response = response.getResponse3();
        boolean inner1Success = (inner1Response != null) && (TextUtils.equals(inner1Response.getCode(), SUCCESS) || TextUtils.equals(inner1Response.getCode(), PARAMS_ERROR));
        boolean inner2Success = (inner2Response != null) && (TextUtils.equals(inner2Response.getCode(), SUCCESS) || TextUtils.equals(inner2Response.getCode(), PARAMS_ERROR));
        boolean inner3Success = (inner3Response != null) && (TextUtils.equals(inner3Response.getCode(), SUCCESS) || TextUtils.equals(inner3Response.getCode(), PARAMS_ERROR));
        if (inner1Success && inner2Success && inner3Success) {
            onSuccess(t);
        } else {
            if (!inner1Success && inner1Response != null) {
                if (TextUtils.equals(inner1Response.getCode(), WINFAE_UNAUTHORIZED)) {
                    BaseServer.logoutServer();
                    onFail(inner1Response.getCode(), inner1Response.getMessage());
                } else {
                    onFail(inner1Response.getCode(), inner1Response.getMessage());
                }
            } else if (!inner2Success && inner2Response != null) {
                if (TextUtils.equals(inner2Response.getCode(), WINFAE_UNAUTHORIZED)) {
                    BaseServer.logoutServer();
                    onFail(inner2Response.getCode(), inner2Response.getMessage());
                } else {
                    onFail(inner2Response.getCode(), inner2Response.getMessage());
                }
            } else if (!inner3Success && inner3Response != null) {
                if (TextUtils.equals(inner3Response.getCode(), WINFAE_UNAUTHORIZED)) {
                    BaseServer.logoutServer();
                    onFail(inner3Response.getCode(), inner3Response.getMessage());
                } else {
                    onFail(inner3Response.getCode(), inner3Response.getMessage());
                }
            } else {
                onFail(UNKNOWN_ERROR, getWFString(R.string.empty_data_from_server));
            }
        }
    }
}
