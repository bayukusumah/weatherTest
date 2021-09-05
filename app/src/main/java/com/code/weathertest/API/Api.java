package com.code.weathertest.API;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface Api {
    @Headers({"Content-Type: application/json"})
    @GET("/find")
    Call<ResponseBody> country(@Query("q") int qry, @Query("appid") String key);
    @Headers({"Content-Type: application/json"})
    @GET("weather")
    Call<ResponseBody> city(@Query("q") String qry, @Query("appid") String key);
    @Headers({"Content-Type: application/json"})
    @GET("weather")
    Call<ResponseBody> koordinat(@Query("lat") double lat,@Query("lon") double lon, @Query("appid") String key);
}
