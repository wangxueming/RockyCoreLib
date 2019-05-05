package com.rocky.core.exception;

/**
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/1/7
 */
public class NotFindLayoutException extends RuntimeException {

    public NotFindLayoutException(Class o) {
        super("大兄弟，" + o.getCanonicalName() + "，你的布局id被吃了？");
    }
}
