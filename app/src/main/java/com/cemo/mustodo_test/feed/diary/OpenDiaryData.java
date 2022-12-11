package com.cemo.mustodo_test.feed.diary;

import com.google.gson.annotations.SerializedName;

public class OpenDiaryData {
    @SerializedName("nickname")
    private String userName;
    @SerializedName("profile")
    private String profileUrl;
    @SerializedName("mymsg")
    private String userMsg;
    @SerializedName("title")
    private String title;
    @SerializedName("contents")
    private String contents;
    @SerializedName("img")
    private String imgUrl;

    public OpenDiaryData(String userName, String title, String userMsg, String contents, String imgUrl, String profileUrl){
        this.userName = userName;
        this.title = title;
        this.contents = contents;
        this.userMsg = userMsg;
        this.imgUrl = imgUrl;
        this.profileUrl = profileUrl;
    }

    public String getUserMsg() {
        return userMsg;
    }

    public String getUserName() {
        return userName;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}