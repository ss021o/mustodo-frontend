package com.cemo.mustodo_test.api.diary;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DiaryServiceInterface {
    @GET("search/diary")
    Call<OpenDiaryResponse> getOpenDiaryList();
}
