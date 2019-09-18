package com.rockyw.net.converter;

/**
 * Created by chenzhaohua on 2017/6/30.
 */

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

final class StringRequestBodyConverter implements Converter<String, RequestBody> {
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/x-www-form-urlencoded; charset=UTF-8");

    StringRequestBodyConverter() {
    }

    @Override public RequestBody convert(String value) throws IOException {
        return RequestBody.create(MEDIA_TYPE, value);
    }
}
