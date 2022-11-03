package com.cemo.mustodo_test.api;

import com.google.gson.annotations.SerializedName;

public class SignUpData {

    @SerializedName("email")
    private String userEmail;

    @SerializedName("name")
    private String userName;

    @SerializedName("password")
    private String userPassword;

    @SerializedName("passwordConfirm")
    private String userPasswordCheck;

    @SerializedName("termsAndConditions")
    private Boolean userTermCheck;

    public String getEmail(){
        return userEmail;
    }

    public String getNick(){
        return userName;
    }

    public SignUpData(String userEmail, String userName, String userPassword, String userPasswordCheck, Boolean userTermCheck) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userPasswordCheck = userPasswordCheck;
        this.userTermCheck = userTermCheck;
    }

}
