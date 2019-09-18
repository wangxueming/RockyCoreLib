package com.rockyw.core.listener;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * 加一层。可以少实现一些代码
 *
 * @author wangxueming
 * @version 1.0.0
 * @date 2018/12/21
 */
public abstract class BaseTextWatcher implements TextWatcher{

    @Override
    public void beforeTextChanged(CharSequence text, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence text, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable text) {
    }
}
