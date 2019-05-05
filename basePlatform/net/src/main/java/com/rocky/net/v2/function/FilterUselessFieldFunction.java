package com.rocky.net.v2.function;

import com.google.gson.Gson;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * 因服务端在 code 为 1 (success) 和不为 1 (fail) 时，数据字段（content/data）类型可能不一致,所以可能会导致 Gson 解析出现异常
 * 所以该 Function 在 code 不为 1 时，过滤掉其他字段，只保留 code 和 message 字段
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/4/17
 */
public class FilterUselessFieldFunction implements Function<String, String> {

    private static final String FILED_CODE = "code";
    private static final String FILED_MESSAGE = "message";

    private Gson mGson;

    public FilterUselessFieldFunction() {
        mGson = new Gson();
    }

    @Override
    public String apply(@NonNull String s) throws Exception {
//        JSONObject json = new JSONObject(s);
//        if (json.has(FILED_CODE) && json.has(FILED_MESSAGE) && json.getInt(FILED_CODE) != BaseObserver.SUCCESS) {
//            BaseResponse response = new BaseResponse();
//            response.code = json.getInt(FILED_CODE);
//            response.message = json.getString(FILED_MESSAGE);
//            s = mGson.toJson(response);
//        }
        return s;
    }

}
