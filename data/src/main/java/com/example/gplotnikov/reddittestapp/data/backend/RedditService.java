package com.example.gplotnikov.reddittestapp.data.backend;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

interface RedditService {
    @FormUrlEncoded
    @POST("https://www.reddit.com/api/v1/access_token")
    Call<ApiTokenResponse> getToken(@Field("grant_type") String grantType, @Field("device_id") String deviceId);

    @GET("/top")
    Call<ApiListingResponse<ApiTopic>> getTop(@Query("limit") int limit, @Query("after") String afterId, @Query("before") String beforeId);
}