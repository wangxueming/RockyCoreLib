package com.rockyw.projectcore.common.bean;

import com.rockyw.projectcore.net.BaseResponse;

/**
 * 合并三个Response
 *
 * @author: WangXueMing
 * @version: 1.0.0
 * @date: 2019/2/18
 */
public class Merge3Response<T1 extends BaseResponse, T2 extends BaseResponse, T3 extends BaseResponse> {
    T1 baseResponse1;
    T2 baseResponse2;
    T3 baseResponse3;

    public Merge3Response(T1 response1, T2 response2, T3 response3) {
        baseResponse1 = response1;
        baseResponse2 = response2;
        baseResponse3 = response3;
    }

    public T1 getResponse1() {
        return baseResponse1;
    }

    public T2 getResponse2() {
        return baseResponse2;
    }

    public T3 getResponse3() {
        return baseResponse3;
    }
}
