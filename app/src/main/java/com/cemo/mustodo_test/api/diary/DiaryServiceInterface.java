package com.cemo.mustodo_test.api.diary;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

public interface DiaryServiceInterface {
    @GET("search/diary")
    Call<OpenDiaryResponse> getOpenDiaryList();

    @POST("{nickname}/diary")
    Call<Object> createDiary(@Path("nickname") String nickname,
                             @Body DiaryRequest body);

    @GET("/")
    Call<Object> index();

    @GET("{nickname}/diary/d/{date}")
    Call<DiaryResponse> getDiaryDay(@Path("nickname") String nickname,
                                    @Path("date") String date);

    @GET("diary")
    Call<OpenDiaryResponse> getDiaryFeed();
}
