package com.cemo.mustodo_test.api.todo;

import com.cemo.mustodo_test.stats.StatData;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StatsResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("data")
    private StatData data;

    @SerializedName("msg")
    private String msg;

    public StatsResponse(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public StatData getData() {
        return  data;
    }

    public String getMsg() {
        return msg;
    }
}
