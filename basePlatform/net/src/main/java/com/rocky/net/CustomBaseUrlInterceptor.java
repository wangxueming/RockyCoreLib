package com.rocky.net;

import android.util.Log;

import java.io.IOException;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 通过配置Header来使RetrofitClient自动适应非公司内部接口的baseUrl地址
 * example:@Headers({"custom_base_url:https://maps.googleapis.com/maps/api/place/nearbysearch/json"})
 *
 * @author wangxueming
 * @version 1.0.0
 * @date 2018/12/14
 */
public class CustomBaseUrlInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        List<String> headerValues = request.headers(BaseServer.HEAD_KEY_CUSTOM_BASE_URL);
        if (headerValues != null && headerValues.size() > 0) {
            builder.removeHeader(BaseServer.HEAD_KEY_CUSTOM_BASE_URL);
            String headerValue = headerValues.get(0);
            HttpUrl newBaseUrl = HttpUrl.parse(headerValue);
            Log.e("bingo", "custom base url: " + headerValue);
            HttpUrl oldHttpUrl = request.url();
            //重建新的HttpUrl，修改需要修改的url部分
            HttpUrl newFullUrl = oldHttpUrl
                    .newBuilder()
                    .scheme(newBaseUrl.scheme())
                    .host(newBaseUrl.host())
                    .port(newBaseUrl.port())
                    .build();
            return chain.proceed(builder.url(newFullUrl).build());
        } else {
            return chain.proceed(request);
        }
    }

}
