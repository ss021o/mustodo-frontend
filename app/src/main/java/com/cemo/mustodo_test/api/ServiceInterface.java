package com.cemo.mustodo_test.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ServiceInterface {
    String URL = "http://192.168.0.93:8080/api/";

    @POST("auth/sign-up")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<SignUpResponse> userSignUp(@Body SignUpData data);

    @POST("auth/login")
    Call<LoginResponse> userLogin(
            @Field("email") String userEmail,
            @Field("password") String userPassword
    );

//    @POST("/user/login")
//    Call<LoginResponse> userLogin(@Body LoginData data);
//
//    @POST("/user/join")
//    Call<JoinResponse> userJoin(@Body JoinData data);
}
