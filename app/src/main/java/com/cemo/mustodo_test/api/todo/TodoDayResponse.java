package com.cemo.mustodo_test.api.todo;

import com.cemo.mustodo_test.todo.TodoData;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TodoDayResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("data")
    private List<TodoDayData> data;

    @SerializedName("msg")
    private String msg;

    public int getCode() {
        return code;
    }

    public List<TodoDayData> getData() {
        return  data;
    }

    public String getMsg() {
        return msg;
    }
}
