package com.rockyw.core.net;

/**
 * RxJava中请求错误时的封装
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/9/18
 */
public class ServerException extends Exception {

    private int code;

    public ServerException(String message) {
        super(message);
    }

    public ServerException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
