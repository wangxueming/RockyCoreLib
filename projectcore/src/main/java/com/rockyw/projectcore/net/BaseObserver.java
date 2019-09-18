package com.rockyw.projectcore.net;

import android.content.Context;
import android.net.ParseException;
import android.text.TextUtils;

import com.google.gson.JsonParseException;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.rockyw.projectcore.common.bean.Merge2Response;
import com.rockyw.core.base.mvp.inter.IView;
import com.rockyw.core.util.ActivityUtils;
import com.rockyw.core.util.logger.L;
import com.rockyw.net.BaseServer;
import com.rockyw.net.R;
import com.rockyw.projectcore.common.bean.Merge3Response;
import com.rockyw.projectcore.common.bean.Merge4Response;

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
     * 公司自定义的逻辑上的code，这里只是举例，还需要根据公司实际情况进行定义调整
     */
    public static final String SUCCESS = "0000";
    public static final String UNKNOWN_ERROR = "9999";
    public static final String PARAMS_ERROR = "9001";
    public static final String WINFAE_UNAUTHORIZED = "4006";
    public static final String NO_NETWORK = "19999";

    /**
     * 对应HTTP的状态码
     * 这是一个基础版本的HTTP状态码定义，我的理解业务返回是一套。HTTP是一套。
     * 应该分开处理。后端应该遵循这样的规则。
     * 首先，遵循HTTP返回值。
     * 其次，遵循公司定义的返回值。
     * 这样，层级清楚。有些朋友会发现部分后端喜欢，二合一。既然本来就是两套。你何必二合一。
     * 一旦碰到HTTP的状态码，还得重新处理一次。还不如提前大家都遵循这样的规则。
     */
    protected static final int UNAUTHORIZED = 401;
    protected static final int FORBIDDEN = 403;
    protected static final int NOT_FOUND = 404;
    protected static final int REQUEST_TIMEOUT = 408;
    protected static final int CONFLICT = 409;
    protected static final int INTERNAL_SERVER_ERROR = 500;
    protected static final int BAD_GATEWAY = 502;
    protected static final int SERVICE_UNAVAILABLE = 503;
    protected static final int GATEWAY_TIMEOUT = 504;

    protected static final String NO_CONTENT = "204";
    protected static final String PARSE_EXCEPTION = "800";
    protected static final String TIME_OUT_EXCEPTION = "801";
    protected static final String CONNECT_EXCEPTION = "802";
    protected static final String UNKNOWN_HOST_EXCEPTION = NO_NETWORK;


    protected Object tag;
    protected Context mContext;

    protected IView mView;
    protected String mNetMsgHint;
    protected IView mDialogView = null;
    protected SmartRefreshLayout rlRefreshLayout = null;

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

    protected BaseObserver(IView view, String hint) {
        mView = view;
        mDialogView = view;
        mNetMsgHint = hint;
    }

    @Override
    public void onStart() {
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
            } else if (t instanceof Merge4Response) {
                handleMerge4(t);
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
            mContext = ActivityUtils.getInstance().getAppContext();
        }
        if (mContext == null) {
            return "BaseObserver中getWFString的Context为Null。你要检查下ActivityUtils的调用顺序";
        }
        return mContext.getString(stringId);
    }

    /**
     * 这是单一的返回值
     * @param t 这是成功的返回值，就会是继承了BaseResponse的某个类
     */
    private void handleSingleResponse(T t) {
        BaseResponse response = (BaseResponse) t;
        if (isResponseSuccess(response.getCode())) {
            onSuccess(t);
        } else {
            //#你可以在这个位置，做一些逻辑判断，比如用户token过期的logoutServer处理
            if (isTokenExpired(response.getCode())) {
                BaseServer.logoutServer();
            }
            onFail(response.getCode(), response.getMessage());
        }
    }

    /**
     * 这是两个返回值一起合并后的返回值
     * @param t 这是成功的返回值，就会是继承了Merge2Response的某个类
     */
    private void handleMerge2(T t) {
        Merge2Response response = (Merge2Response) t;
        BaseResponse inner1Resp = response.getResponse1();
        BaseResponse inner2Resp = response.getResponse2();
        boolean inner1Success = (inner1Resp != null) && isResponseSuccess(inner1Resp.getCode());
        boolean inner2Success = (inner2Resp != null) && isResponseSuccess(inner2Resp.getCode());
        if (inner1Success && inner2Success) {
            onSuccess(t);
        } else {
            if (!inner1Success && inner1Resp != null) {
                //#你可以在这个位置，做一些逻辑判断，比如用户token过期的logoutServer处理
                if (isTokenExpired(inner1Resp.getCode())) {
                    BaseServer.logoutServer();
                }
                onFail(inner1Resp.getCode(), inner1Resp.getMessage());
            } else if (!inner2Success && inner2Resp != null) {
                //#你可以在这个位置，做一些逻辑判断，比如用户token过期的logoutServer处理
                if (isTokenExpired(inner1Resp.getCode())) {
                    BaseServer.logoutServer();
                }
                onFail(inner2Resp.getCode(), inner2Resp.getMessage());
            } else {
                onFail(UNKNOWN_ERROR, getWFString(R.string.empty_data_from_server));
            }
        }
    }

    /**
     * 这是三个返回值一起合并后的返回值
     * @param t 这是成功的返回值，就会是继承了Merge3Response的某个类
     */
    private void handleMerge3(T t) {
        Merge3Response response = (Merge3Response) t;
        BaseResponse inner1Resp = response.getResponse1();
        BaseResponse inner2Resp = response.getResponse2();
        BaseResponse inner3Resp = response.getResponse3();
        boolean inner1Success = (inner1Resp != null) && isResponseSuccess(inner1Resp.getCode());
        boolean inner2Success = (inner2Resp != null) && isResponseSuccess(inner2Resp.getCode());
        boolean inner3Success = (inner3Resp != null) && isResponseSuccess(inner3Resp.getCode());
        if (inner1Success && inner2Success && inner3Success) {
            onSuccess(t);
        } else {
            if (!inner1Success && inner1Resp != null) {
                //#你可以在这个位置，做一些逻辑判断，比如用户token过期的logoutServer处理
                if (isTokenExpired(inner1Resp.getCode())) {
                    BaseServer.logoutServer();
                }
                onFail(inner1Resp.getCode(), inner1Resp.getMessage());
            } else if (!inner2Success && inner2Resp != null) {
                //#你可以在这个位置，做一些逻辑判断，比如用户token过期的logoutServer处理
                if (isTokenExpired(inner2Resp.getCode())) {
                    BaseServer.logoutServer();
                }
                onFail(inner2Resp.getCode(), inner2Resp.getMessage());
            } else if (!inner3Success && inner3Resp != null) {
                //#你可以在这个位置，做一些逻辑判断，比如用户token过期的logoutServer处理
                if (isTokenExpired(inner3Resp.getCode())) {
                    BaseServer.logoutServer();
                }
                onFail(inner3Resp.getCode(), inner3Resp.getMessage());
            } else {
                onFail(UNKNOWN_ERROR, getWFString(R.string.empty_data_from_server));
            }
        }
    }

    private void handleMerge4(T t) {
        //#这是两个返回值一起合并后的返回值
        Merge4Response response = (Merge4Response) t;
        BaseResponse inner1Resp = response.getResponse1();
        BaseResponse inner2Resp = response.getResponse2();
        BaseResponse inner3Resp = response.getResponse3();
        BaseResponse inner4Resp = response.getResponse4();
        boolean inner1Success = (inner1Resp != null) && isResponseSuccess(inner1Resp.getCode());
        boolean inner2Success = (inner2Resp != null) && isResponseSuccess(inner2Resp.getCode());
        boolean inner3Success = (inner3Resp != null) && isResponseSuccess(inner3Resp.getCode());
        boolean inner4Success = (inner4Resp != null) && isResponseSuccess(inner4Resp.getCode());
        if (inner1Success && inner2Success && inner3Success && inner4Success) {
            onSuccess(t);
        } else {
            if (!inner1Success && inner1Resp != null) {
                //#你可以在这个位置，做一些逻辑判断，比如用户token过期的logoutServer处理
                if (isTokenExpired(inner1Resp.getCode())) {
                    BaseServer.logoutServer();
                }
                onFail(inner1Resp.getCode(), inner1Resp.getMessage());
            } else if (!inner2Success && inner2Resp != null) {
                //#你可以在这个位置，做一些逻辑判断，比如用户token过期的logoutServer处理
                if (isTokenExpired(inner2Resp.getCode())) {
                    BaseServer.logoutServer();
                }
                onFail(inner2Resp.getCode(), inner2Resp.getMessage());
            } else if (!inner3Success && inner3Resp != null) {
                //#你可以在这个位置，做一些逻辑判断，比如用户token过期的logoutServer处理
                if (isTokenExpired(inner3Resp.getCode())) {
                    BaseServer.logoutServer();
                }
                onFail(inner3Resp.getCode(), inner3Resp.getMessage());
            } else if (!inner4Success && inner4Resp != null) {
                //#你可以在这个位置，做一些逻辑判断，比如用户token过期的logoutServer处理
                if (isTokenExpired(inner4Resp.getCode())) {
                    BaseServer.logoutServer();
                }
                onFail(inner4Resp.getCode(), inner4Resp.getMessage());
            } else {
                onFail(UNKNOWN_ERROR, getWFString(R.string.empty_data_from_server));
            }
        }
    }

    /**
     * 这是一个框架性的设计，用户自定义什么情况才是一个成功的情况。
     * 但凡一个公司的后台框架，有一套合理的设计
     * 都会很好的约定什么时候请求是成功的。而成功的请求并不代表这次的结果就是合法的。只是说请求成功了。
     * 但是内部还需要考虑业务属性的定义。
     *
     * @param code 传入这次请求 返回的类型
     * @return true则是合理合法的请求
     */
    protected boolean isResponseSuccess(String code) {
        return TextUtils.equals(code, SUCCESS);
    }

    /**
     * 判断是否token过期。
     * 需要自定义判断的条件
     *
     * @param code
     * @return true 表示token已经过期了
     */
    private boolean isTokenExpired(String code) {
        return TextUtils.equals(code, WINFAE_UNAUTHORIZED);
    }
}
