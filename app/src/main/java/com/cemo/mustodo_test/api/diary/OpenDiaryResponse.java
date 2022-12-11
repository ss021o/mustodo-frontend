package com.cemo.mustodo_test.api.diary;

import com.cemo.mustodo_test.api.todo.TodoDayData;
import com.cemo.mustodo_test.feed.diary.OpenDiaryData;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OpenDiaryResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("data")
    private List<OpenDiaryData> data;

    public List<OpenDiaryData> getData() {
        return  data;
    }

    public int getCode() {
        return code;
    }
}
