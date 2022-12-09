package com.cemo.mustodo_test.api.todo;

import com.cemo.mustodo_test.api.UserData;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;

public class TodoResponse {

//    @SerializedName("todoId")
//    private Long todoId;
//
//    @SerializedName("content")
//    private String content;
//
//    @SerializedName("achieved")
//    private boolean check;
//
//    public Long getTodoId() {
//        return todoId;
//    }
//
//    public String getContent() {
//        return content;
//    }
//
//    public boolean isCheck() {
//        return check;
//    }

    @SerializedName("code")
    private int code;

    @SerializedName("data")
    private JSONArray data;

    public int getCode() {
        return code;
    }

    public JSONArray getResult() {
        return data;
    }

}
