package com.cemo.mustodo_test.api.todo;

import com.cemo.mustodo_test.api.SignUpData;
import com.cemo.mustodo_test.api.SignUpResponse;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TodoServiceInterface {

    @POST("{nickname}/todo")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<TodoResponse> setTodo(
            @Path(value = "nickname") String nickname,
            @Body TodoDayData data
    );

    @POST("{nickname}/todogroup")
    Call<TodoGroupResponse> setTodoGroup(
            @Path(value = "nickname") String nickname,
            @Body TodoGroupData data
    );

    @GET("{nickname}/todogroup")
    Call<TodoGroupResponse> getTodoGroup(@Path(value = "nickname") String nickname);

    @GET("{nickname}/todo/t")
    Call<TodoDayResponse> getTodo(@Path(value = "nickname") String nickname);

    @GET("{nickname}/todo/d/{date}")
    Call<TodoDayResponse> getTodoDay(
            @Path("nickname") String nickname,
            @Path("date") String date
    );

    @GET("{nickname}/todo/m/{date}")
    Call<TodoMonthResponse> getTodoMonth(
            @Path("nickname") String nickname,
            @Path("date") String date
    );


    @GET("todo/{date}")
    Call<List<CategoryTodoResponse>> todoByDate(@Path(value = "date") String date);

    @GET("search/todo")
    Call<OpenResponse> getOpenTodoList();

    @GET("{nickname}/todo/stat/t")
    Call<StatsResponse> getTodoTodayStats(
            @Path("nickname") String nickname
    );

    @GET("{nickname}/todo/stat/m/{date}/all")
    Call<StatsMonthResponse> getTodoMonthTotalStats(
            @Path("nickname") String nickname,
            @Path("date") String date
    );

    @GET("{nickname}/todo/stat/m/{date}/checked")
    Call<StatsMonthResponse> getTodoMonthCheckedStats(
            @Path("nickname") String nickname,
            @Path("date") String date
    );
}
