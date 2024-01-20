package com.common.api.provider;

import java.util.Map;

import com.common.api.response.RestClientResponseMapper;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface RetrofitClientService {

    @GET
    Call<RestClientResponseMapper> retrofitMethodGet(@Url String url,
    									   @HeaderMap Map<String, String> headers,
    									   @QueryMap(encoded = true) Map<String, String> params);

    @POST
    Call<RestClientResponseMapper> retrofitMethodPost(@Url String url,
    									    @HeaderMap Map<String, String> headers,
    									    @Body Object body);

    @PUT
    Call<RestClientResponseMapper> retrofitMethodPut(@Url String url,
    									   @HeaderMap Map<String, String> headers,
    									   @Body Object body);


    @DELETE
    Call<RestClientResponseMapper> retrofitMethodDelete(@Url String url,
    									   	  @HeaderMap Map<String, String> headers,
    									      @QueryMap(encoded = true) Map<String, String> params);

}