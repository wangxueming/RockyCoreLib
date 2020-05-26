package com.rockyw.couriershop.net;

import com.rockyw.couriershop.bean.CompanyListBody;
import com.rockyw.couriershop.bean.CompanyListResponse;
import com.rockyw.couriershop.bean.StockQuotationBody;
import com.rockyw.couriershop.bean.StockQuotationResponse;
import com.rockyw.projectcore.common.bean.VersionResponse;
import com.rockyw.projectcore.net.BaseResponse;

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
