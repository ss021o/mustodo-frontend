package com.cemo.mustodo_test.api.diary;

import com.google.gson.annotations.SerializedName;

public class DiaryDayData {

    @SerializedName("id")
    private int id;

    @SerializedName("user_id")
    private int user_id;

    @SerializedName("nickname")
    private String nickname;

    @SerializedName("profile")
    private String profile;

    @SerializedName("mymsg")
    private String mymsg;

    @SerializedName("title")
    private String title;

    @SerializedName("img")
    private String img;

    @SerializedName("contents")
    private String contents;

    @SerializedName("created")
    private long created;

    @SerializedName("isOpen")
    private Boolean isOpen;

    public String getProfile() { return this.profile; }

    public String getMymsg() { return this.mymsg; }

    public String getNickname() { return this.nickname; }

    public int getUser_id() { return this.user_id; }

    public String getTitle() { return this.title; }

    public int getId() { return this.id; }

    public Boolean getOpen() { return this.isOpen; }

    public String getContents() { return this.contents; }

    public long getCreated() { return this.created; }

    public String getImg() { return this.img; }

    public void setId(int id) { this.id = id; }

    public void setTitle(String title) { this.title = title; }

    public void setContents(String contents) { this.contents = contents; }

    public void setCreated(int created) { this.created = created; }

    public void setImg(String img) { this.img = img;}

    public void setMymsg(String mymsg) { this.mymsg = mymsg; }

    public void setNickname(String nickname) { this.nickname = nickname; }

    public void setOpen(Boolean open) { isOpen = open; }

    public void setProfile(String profile) { this.profile = profile; }

    public void setUser_id(int user_id) { this.user_id = user_id; }

    public DiaryDayData(int id, int user_id, String nickname, String profile, String mymsg, String title, String img, String contents,  long created){
        this.id = id;
        this.user_id = user_id;
        this.nickname = nickname;
        this.profile = profile;
        this.mymsg = mymsg;
        this.title = title;
        this.img = img;
        this.contents = contents;
        this.created =created;
    }
}
