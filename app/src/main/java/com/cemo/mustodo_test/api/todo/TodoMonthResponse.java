package com.cemo.mustodo_test.api.todo;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;

import java.util.List;

public class TodoMonthResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("data")
    private List<TodoMonthData> data;

    @SerializedName("msg")
    private String msg;

    public TodoMonthResponse(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public List<TodoMonthData> getData() {
        return  data;
    }

    public String getMsg() {
        return msg;
    }
}
