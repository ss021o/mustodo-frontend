package com.cemo.mustodo_test.api;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("data")
    private String data;

    @SerializedName("message")
    private String message;

    public int getCode() {
        return code;
    }

    public String getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
