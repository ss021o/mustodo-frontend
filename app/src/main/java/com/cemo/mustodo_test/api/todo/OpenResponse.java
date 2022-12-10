package com.cemo.mustodo_test.api.todo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OpenResponse {

    @SerializedName("code")
    private int code;

    @SerializedName("msg")
    private List<TodoDayData> msg;

    public List<TodoDayData> getMsg() {
        return  msg;
    }

    public int getCode() {
        return code;
    }

}
