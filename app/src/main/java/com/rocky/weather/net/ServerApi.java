package com.rocky.weather.net;

import com.rocky.projectcore.common.bean.QualifyInvestorResponse;
import com.rocky.projectcore.common.bean.VersionResponse;
import com.rocky.projectcore.net.BaseResponse;

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

    @FormUrlEncoded
    @POST("/api/app/version/getLatestVersion")
    Observable<VersionResponse> getLastestVersion(@Field("packageName") String packageName,
                                                  @Field("currentVersionNumber") String currentVersionNumber,
                                                  @Field("platform") int platform,
                                                  @Field("channel") String channel);


    @FormUrlEncoded
    @POST("/api/client/auth/investors/info/get")
    Observable<QualifyInvestorResponse> getQualifyInvestorInfo(@Field("accessToken") String accessToken);

    @FormUrlEncoded
    @POST("/api/agreement/version")
    Observable<BaseResponse> getAgreementVersion(@Field("username") String userName);


}
