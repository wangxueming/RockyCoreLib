package com.rockyw.net.v2.function;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * 从 Observer<T> 中获取 T 的具体类型
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/4/17
 */
public class GenericFunction<T> implements Function<String, T> {

    private Observer<T> mObserver;

    private Gson mGson;

    public GenericFunction(Observer<T> observer) {
        mObserver = observer;
        mGson = new Gson();
    }

    @Override
    public T apply(@NonNull String s) throws Exception {
        Class<T> clazz = getClassFromInterface(mObserver);
        if (clazz == null) {
            throw new Exception("获取接口泛型参数异常");
        }
        return mGson.fromJson(s, clazz);
    }

    /**
     * 获取泛型
     *
     * @param observer
     * @param <T>
     * @return
     */
    private <T> Class<T> getClassFromInterface(Observer<T> observer) {
        try {
            Type[] interfaceTypes = observer.getClass().getGenericInterfaces();
            Type type;
            if (interfaceTypes == null || interfaceTypes.length == 0) { //类实现了Observer这个接口
                type = observer.getClass().getGenericSuperclass();
            } else { //直接实现Observer
                type = interfaceTypes[0];
            }
            if (ParameterizedType.class.isAssignableFrom(type.getClass())) {
                Type item = (((ParameterizedType) type).getActualTypeArguments())[0];
                return ((Class<T>) item);
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

}
