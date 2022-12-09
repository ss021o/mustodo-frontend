package com.cemo.mustodo_test.api.todo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TodoGroupResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("id")
    private int id;

    @SerializedName("data")
    private List<TodoGroupData> data;

    @SerializedName("msg")
    private String msg;

    @SerializedName("success")
    private String success;


    public int getCode() {
        return code;
    }
    public int getId() {
        return id;
    }

    public List<TodoGroupData> getData() {
        return  data;
    }

    public String getMsg() {
        return msg;
    }

    public String getSuccess() {
        return success;
    }
}
