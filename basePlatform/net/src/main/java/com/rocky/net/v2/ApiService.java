package com.rocky.net.v2;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * 
 * 
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/4/17
 */
public interface ApiService {

    @Headers({Server.HEADER_KEY_AUTHOR_WAY + ":" + Server.AUTHOR_WAY_NORMAL})
    @GET
    Observable<String> get(
            @Url String url,
            @QueryMap Map<String, Object> params
    );

    @Headers({Server.HEADER_KEY_AUTHOR_WAY + ":" + Server.AUTHOR_WAY_TEAM})
    @GET
    Observable<String> get2(
            @Url String url,
            @QueryMap Map<String, Object> params
    );

    @GET
    Observable<String> get3(@Url String url);

    @FormUrlEncoded
    @Headers({Server.HEADER_KEY_AUTHOR_WAY + ":" + Server.AUTHOR_WAY_NORMAL})
    @POST
    Observable<String> post(
            @Url String url,
            @Body RequestBody body
    );

    @FormUrlEncoded
    @Headers({Server.HEADER_KEY_AUTHOR_WAY + ":" + Server.AUTHOR_WAY_TEAM})
    @POST
    Observable<String> post2(
            @Url String url,
            @Body RequestBody body
    );

    @Headers({Server.HEADER_KEY_AUTHOR_WAY + ":" + Server.AUTHOR_WAY_NORMAL})
    @PUT
    Observable<String> put(
            @Url String url,
            @Body RequestBody body
    );

    @Headers({Server.HEADER_KEY_AUTHOR_WAY + ":" + Server.AUTHOR_WAY_TEAM})
    @PUT
    Observable<String> put2(
            @Url String url,
            @Body RequestBody body
    );

    /**
     * delete带body
     */
    @Headers({Server.HEADER_KEY_AUTHOR_WAY + ":" + Server.AUTHOR_WAY_TEAM})
    @HTTP(method = "DELETE", hasBody = true)
    Observable<String> deleteWithBody(
            @Url String url,
            @Body RequestBody body
    );

    @Headers({Server.HEADER_KEY_AUTHOR_WAY + ":" + Server.AUTHOR_WAY_NORMAL})
    @DELETE
    Observable<String> delete(
            @Url String url,
            @QueryMap Map<String, Object> params
    );

    @Headers({Server.HEADER_KEY_AUTHOR_WAY + ":" + Server.AUTHOR_WAY_TEAM})
    @DELETE
    Observable<String> delete2(
            @Url String url,
            @QueryMap Map<String, Object> params
    );

    @Headers({Server.HEADER_KEY_AUTHOR_WAY + ":" + Server.AUTHOR_WAY_NORMAL})
    @PATCH
    Observable<String> patch(
            @Url String url,
            @Body RequestBody body
    );


    @Headers({Server.HEADER_KEY_AUTHOR_WAY + ":" + Server.AUTHOR_WAY_TEAM})
    @PATCH
    Observable<String> patch2(
            @Url String url,
            @Body RequestBody body
    );

}
