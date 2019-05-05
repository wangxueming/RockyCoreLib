package com.rocky.net.converter;

/**
 * Created by chenzhaohua on 2017/6/30.
 */

/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.util.Log;

import com.google.gson.TypeAdapter;
import com.rocky.core.util.logger.L;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Converter;

final class StringResponseBodyConverter implements Converter<ResponseBody, java.lang.String> {

    StringResponseBodyConverter() {
    }

    @Override
    public String convert(ResponseBody value) throws IOException {
        return value.string();
    }

    private void printLog(ResponseBody value) {
        BufferedSource source = value.source();
        try {
            source.request(Long.MAX_VALUE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Buffer buffer = source.buffer();
        Charset utf8 = Charset.forName("UTF-8");
        Log.d("REQUEST_JSON", buffer.clone().readString(utf8));
    }
}
