package com.rockyw.weather.net;

import com.rockyw.projectcore.common.bean.QualifyInvestorResponse;
import com.rockyw.projectcore.common.bean.VersionResponse;
import com.rockyw.projectcore.net.BaseResponse;
import com.rockyw.weather.bean.CompanyListBody;
import com.rockyw.weather.bean.CompanyListResponse;
import com.rockyw.weather.bean.StockQuotationBody;
import com.rockyw.weather.bean.StockQuotationResponse;
import com.rockyw.weather.data.StockQuotationInfo;

import io.reactivex.Observable;
import retrofit2.http.Body;
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

    /**
     * 获取股市所有公司的列表
     * @param queryBody
     * @return
     */
    @POST("/")
    Observable<CompanyListResponse> getCompaniesInfoList(@Body CompanyListBody queryBody);

    /**
     * 获取单只股票的行情
     * @param queryBody
     * @return
     */
    @POST("/")
    Observable<StockQuotationResponse> getSingleStockInfo(@Body StockQuotationBody queryBody);

}
