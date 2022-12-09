package com.cemo.mustodo_test.api;

import com.google.gson.annotations.SerializedName;

public class UserData {

    @SerializedName("id")
    private int userId;

    @SerializedName("email")
    private String userEmail;

    //@SerializedName("name")
    @SerializedName("nickname")
    private String userName;

    @SerializedName("mymsg")
    private String userMsg;

    @SerializedName("profile")
    private String userProfile;

    public UserData getALLdata(){
        return this;
    }

    public int getId(){
        return userId;
    }

    public String getEmail(){
        return userEmail;
    }

    public String getNickname(){
        return userName;
    }

    public String getMsg(){
        return userMsg;
    }

    public String getProfile(){
        return userProfile;
    }

    public UserData(String userEmail) {
        this.userEmail = userEmail;
    }

    public UserData(int userId, String userEmail, String userName, String userMsg, String userProfile) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.userName = userName;
        this.userMsg = userMsg;
        this.userProfile = userProfile;
    }
}
