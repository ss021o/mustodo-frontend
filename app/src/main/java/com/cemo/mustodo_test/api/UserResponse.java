package com.cemo.mustodo_test.api;

import android.content.ContentValues;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class UserResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("result")
    private UserData result;

    public int getCode() {
        return code;
    }

    public UserData getResult() {
        return result;
    }

}
