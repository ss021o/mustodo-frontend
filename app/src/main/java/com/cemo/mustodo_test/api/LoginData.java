package com.cemo.mustodo_test.api;

import com.google.gson.annotations.SerializedName;

public class LoginData {
    @SerializedName("email")
    private String userEmail;

    @SerializedName("password")
    private String userPassword;

    public String getEmail(){
        return userEmail;
    }

    public LoginData(String userEmail, String userPassword) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }
}
