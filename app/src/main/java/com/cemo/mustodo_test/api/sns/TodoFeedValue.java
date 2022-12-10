package com.cemo.mustodo_test.api.sns;


import com.google.gson.annotations.SerializedName;

public class TodoFeedValue {
    @SerializedName("content")
    private String content;

    @SerializedName("color")
    private String color;

    public String getContent() {
        return content;
    }

    public String getColor() {
        return color;
    }
}
