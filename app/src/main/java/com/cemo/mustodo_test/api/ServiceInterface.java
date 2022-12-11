package com.cemo.mustodo_test.api;

import com.cemo.mustodo_test.api.todo.TodoGroupData;
import com.cemo.mustodo_test.popular.MsgData;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ServiceInterface {
    //String URL = "http://192.168.0.93:8080/api/";
    String URL = "http://192.168.0.93:50471/api/";


    //@POST("auth/sign-up")
    @POST("register")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<SignUpResponse> userSignUp(@Body SignUpData data);

    //@POST("auth/login")
    @POST("login")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<LoginResponse> userLogin(@Body LoginData data);

    @POST("user")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<UserResponse> userCheck(@Body UserData data);

    //@PATCH("auth/authorization")
    @POST("authorization")
    Call<AuthResponse> userEmailAuth(@Body AuthData data);


    @POST("{nickname}/goal")
    Call<ResponseBody> setUserGoal(
            @Path(value = "nickname") String nickname,
            @Body GoalData mymsg
    );
}
