package com.cemo.mustodo_test.api.todo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TodoServiceInterface {
    @GET("todo/{date}")
    Call<List<CategoryTodoResponse>> todoByDate(@Path(value = "date") String date);
}
