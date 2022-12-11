package com.cemo.mustodo_test.api.popular;


import retrofit2.Call;
import retrofit2.http.GET;

public interface PopularServiceInterface {
    @GET("famous/today")
    Call<PopularResponse> getTodayFamous();

    @GET("famous/all")
    Call<PopularResponse> getFamousAll();
}
