package com.cemo.mustodo_test.api.sns;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TodoFeedResponse {
    @SerializedName("todoFeed")
    private List<TodoFeedDto> todoFeed;

    public List<TodoFeedDto> getTodoFeed() {
        return todoFeed;
    }
}
