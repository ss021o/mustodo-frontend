package com.cemo.mustodo_test.api.todo;

import com.cemo.mustodo_test.stats.StatData;
import com.cemo.mustodo_test.stats.StatMonthData;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StatsMonthResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("data")
    private List<StatData> data;

    @SerializedName("msg")
    private String msg;

    public List<StatData> getData() {
        return  data;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
