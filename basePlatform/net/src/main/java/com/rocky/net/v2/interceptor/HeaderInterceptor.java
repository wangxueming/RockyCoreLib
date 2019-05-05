package com.rocky.net.v2.interceptor;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 通过配置Header来使RetrofitClient自动适应非公司内部接口的baseUrl地址
 * example:@Headers({"custom_base_url:https://maps.googleapis.com/maps/api/place/nearbysearch/json"})
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/1/23
 */
public class HeaderInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();

//        UserInfo userInfo = InfoManger.getInstance().getUserInfo();
//        if (userInfo != null) {
//            List<String> headerValues = request.headers(Server.HEADER_KEY_AUTHOR_WAY);
//            if (headerValues != null && headerValues.size() > 0) {
//                String headerValue = headerValues.get(0);
//                builder.removeHeader(Server.HEADER_KEY_AUTHOR_WAY);
//                if (headerValue.equals(Server.AUTHOR_WAY_NORMAL)) {
//                    builder.addHeader(Server.HEAD_KEY_TOKEN, "token " + InfoManger.getInstance().getUserInfo().getToken());
//                } else if (headerValue.equals(Server.AUTHOR_WAY_TEAM)) {
//                    builder.addHeader(Server.HEAD_KEY_TOKEN, InfoManger.getInstance().getUserInfo().getToken());
//                }
//            }
//        }
//
//        SystemInfo systemInfo = InfoManger.getInstance().getSystemInfo();
//        if (systemInfo != null) {
//            builder.addHeader(Server.HEAD_KEY_APP_VERSION, String.valueOf(systemInfo.getAppVersion()));
//            builder.addHeader(Server.HEAD_KEY_UG, systemInfo.getUserAgent());
//        }
//        builder.addHeader(Server.HEAD_KEY_ACCEPT_LANGUAGE, SystemUtil.getAcceptLanguage());

        Request newRequest = builder.build();
        return chain.proceed(newRequest);
    }

}
