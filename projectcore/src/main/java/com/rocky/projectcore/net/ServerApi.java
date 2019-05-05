package com.rocky.projectcore.net;

import com.rocky.projectcore.common.bean.QualifyInvestorResponse;
import com.rocky.projectcore.common.bean.VersionResponse;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author: WangXueMing
 * @version: 1.0.0
 * @date: 2019/1/4
 */
public interface ServerApi {

    /**
     * 设置合格投资者状态
     * @param accessToken
     * @param type
     * @return
     */
    @FormUrlEncoded
    @POST("/api/client/auth/investors/agreement/set")
    Observable<QualifyInvestorResponse> setQualifyInvestorInfo(@Field("accessToken") String accessToken, @Field("type") int type);
}
