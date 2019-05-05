package com.rocky.net.converter;

import android.support.annotation.Nullable;

import com.rocky.net.ResponseFormat;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Json或xml类型
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/4/17
 */
public class JsonOrXmlConverterFactory extends Converter.Factory {

    private final Converter.Factory xmlFactory = StringConverterFactory.create();
    private final Converter.Factory jsonFactory = GsonConverterFactory.create();

    public static JsonOrXmlConverterFactory create() {
        return new JsonOrXmlConverterFactory();
    }

    @Nullable
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        for (Annotation annotation : annotations) {
            if (!(annotation instanceof ResponseFormat)) {
                continue;
            }
            String value = ((ResponseFormat) annotation).value();
            if ("string".equals(value)) {
                return xmlFactory.responseBodyConverter(type, annotations, retrofit);
            } else {
                return jsonFactory.responseBodyConverter(type, annotations, retrofit);
            }
        }

        return null;
    }
}