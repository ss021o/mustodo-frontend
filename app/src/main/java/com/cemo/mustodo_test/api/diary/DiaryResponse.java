package com.cemo.mustodo_test.api.diary;

import com.cemo.mustodo_test.diary.DiaryData;
import com.google.gson.annotations.SerializedName;


import java.util.List;

public class DiaryResponse {

    @SerializedName("code")
    private int code;

    @SerializedName("data")
    private List<DiaryData> data;

    @SerializedName("msg")
    private String msg;

    public int getCode() {
        return code;
    }

    public List<DiaryData> getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }
}
