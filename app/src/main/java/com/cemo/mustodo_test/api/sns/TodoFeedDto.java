package com.cemo.mustodo_test.api.sns;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TodoFeedDto {
    @SerializedName("userId")
    private Long userId;

    @SerializedName("userName")
    private String userName;

    @SerializedName("profilePath")
    private String profilePath;

    @SerializedName("todo")
    private List<TodoFeedValue> todo;

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public List<TodoFeedValue> getTodo() {
        return todo;
    }
}
