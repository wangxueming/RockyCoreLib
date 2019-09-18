package com.rockyw.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 缓存拦截器
 *
 * @author wangxueming
 * @version 1.0.0
 * @date 2018/12/14
 */
public class CacheInterceptor implements Interceptor {

    /**
     * max-age    : 表示当访问此网页后的max-age秒内再次访问不会去服务器请求，其功能与Expires类似，只是Expires是根据某个特定日期值做比
     * max-stale  : 允许读取过期时间必须小于max-stale 值的缓存对象
     */
    /**
     * //有效时间为1分钟
     */
    private final static int MAX_AGE = 60 * 60;
    /**
     * //有效时间为4周，
     */
    private final static int MAX_STALE = 60 * 60 * 24 * 28;

    private Context mContext;

    public CacheInterceptor(Context context) {
        mContext = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        if (!isNetWorkConnected(mContext)) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .url(chain.request().url()).build();
        }

        Response response = chain.proceed(request);


        if (isNetWorkConnected(mContext)) {
            response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, max-age=" + MAX_AGE)
                    .build();
        } else {
            response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + MAX_STALE)
                    .build();
        }
        return response;
    }


    /**
     * 检查网络是否已连接
     *
     * @param context
     * @return
     */
    private boolean isNetWorkConnected(Context context) {

        if (context == null) {
            return false;
        }

        try {
            ConnectivityManager ccm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo ni = ccm.getActiveNetworkInfo();
            return ni.isConnected();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
