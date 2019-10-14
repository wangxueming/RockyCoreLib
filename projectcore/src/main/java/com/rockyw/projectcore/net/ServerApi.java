package com.rockyw.projectcore.net;

import com.rockyw.projectcore.common.bean.AnnounceDetailResponse;
import com.winfae.projectcore.common.bean.QualifyInvestorResponse;

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

    /**
     * 获取公告详情
     * @param noticeSn
     * @param type
     * @return
     */
    @FormUrlEncoded
    @POST("/api/notice/detail")
    Observable<AnnounceDetailResponse> getAnnounceDetail(@Field("noticeSn")int noticeSn, @Field("type") int type);
}
