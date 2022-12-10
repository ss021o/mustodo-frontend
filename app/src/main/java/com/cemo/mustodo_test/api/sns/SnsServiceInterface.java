package com.cemo.mustodo_test.api.sns;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface SnsServiceInterface {
    @GET("sns/todo?size=5")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<TodoFeedResponse> getTodoFeed(@Query("page") int page);
}
