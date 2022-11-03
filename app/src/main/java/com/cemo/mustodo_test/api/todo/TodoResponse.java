package com.cemo.mustodo_test.api.todo;

import com.google.gson.annotations.SerializedName;

public class TodoResponse {

    @SerializedName("todoId")
    private Long todoId;

    @SerializedName("content")
    private String content;

    @SerializedName("achieved")
    private boolean check;

    public Long getTodoId() {
        return todoId;
    }

    public String getContent() {
        return content;
    }

    public boolean isCheck() {
        return check;
    }
}
