package com.cemo.mustodo_test.api.diary;

import com.cemo.mustodo_test.api.todo.TodoDayData;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OpenDiaryResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("msg")
    private List<DiaryDayData> msg;

    public List<DiaryDayData> getMsg() {
        return  msg;
    }

    public int getCode() {
        return code;
    }
}
