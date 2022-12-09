package com.cemo.mustodo_test.api;

import com.google.gson.annotations.SerializedName;

public class AuthData {
    @SerializedName("email")
    private String userEmail;

    @SerializedName("code")
    private String userAuth;

    public AuthData(String userEmail, String userAuth) {
        this.userEmail = userEmail;
        this.userAuth = userAuth;
    }
}
