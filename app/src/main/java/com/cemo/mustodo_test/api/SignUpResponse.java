package com.cemo.mustodo_test.api;

import com.google.gson.annotations.SerializedName;

public class SignUpResponse {
//    @SerializedName("code")
//    private int code;
//
//    @SerializedName("message")
//    private String message;

    @SerializedName("errorCode")
    private String errorCode;
//
//
//    public int getCode() {
//        return code;
//    }
//
//    public String getMessage() {
//        return message;
//    }

    public String getErrorCode() {
        return errorCode;
    }
}
