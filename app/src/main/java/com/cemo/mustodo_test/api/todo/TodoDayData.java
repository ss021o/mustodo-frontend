package com.cemo.mustodo_test.api.todo;

import com.google.gson.annotations.SerializedName;


public class TodoDayData {
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

    @SerializedName("group_id")
    private int group_id;

    @SerializedName("title")
    private String title;

    @SerializedName("todoDate")
    private String todoDate;

    @SerializedName("todoTime")
    private String todoTime;

    @SerializedName("isCheck")
    private String isCheck;

    @SerializedName("isOpen")
    private Boolean isOpen;

    @SerializedName("isLevel")
    private int isLevel;

    @SerializedName("cate")
    private String groupName;

    @SerializedName("color")
    private String groupColor;

    public int getId() { return id; }

    public String getTitle() { return title; }

    public String getGroupColor() { return groupColor; }

    public String getCheck() { return isCheck; }

    public Boolean getOpen() { return isOpen; }

    public int getGroup_id() { return group_id; }

    public int getIsLevel() { return isLevel; }

    public int getUser_id() { return user_id; }

    public String getNickname() { return nickname; }

    public String getGroupName() {
        return groupName;
    }

    public String getMymsg() {
        return mymsg;
    }

    public String getProfile() {
        return profile;
    }

    public String getTodoDate(){
        return todoDate;
    }

    public String getTodoTime(){
        return todoTime;
    }


    public TodoDayData(int group_id, String title, String todoDate, String todoTime, boolean isOpen, int isLevel){
        this.group_id = group_id;
        this.title = title;
        this.todoDate = todoDate;
        this.todoTime = todoTime;
        this.isOpen = isOpen;
        this.isLevel = isLevel;
    }


    public TodoDayData(int id, int user_id, String nickname, String profile, String mymsg, int group_id, String title, String todoDate, String todoTime, String isCheck, Boolean isOpen, int isLevel) {
        this.id = id;
        this.user_id = user_id;
        this.nickname = nickname;
        this.profile = profile;
        this.mymsg = mymsg;
        this.group_id = group_id;
        this.title = title;
        this.todoDate = todoDate;
        this.todoTime = todoTime;
        this.isCheck = isCheck;
        this.isOpen = isOpen;
        this.isLevel =isLevel;
    }


    public TodoDayData(int id, int user_id, int group_id, String title, String todoDate, String todoTime, String isCheck, Boolean isOpen, int isLevel, String groupName, String groupColor) {
       this.id = id;
       this.user_id = user_id;
       this.group_id = group_id;
       this.title = title;
       this.todoDate = todoDate;
       this.todoTime = todoTime;
       this.isCheck = isCheck;
       this.isOpen = isOpen;
       this.isLevel =isLevel;
       this.groupName = groupName;
       this.groupColor = groupColor;
    }

    @Override
    public String toString() {
        return "TodoDayData{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", nickname='" + nickname + '\'' +
                ", profile='" + profile + '\'' +
                ", mymsg='" + mymsg + '\'' +
                ", group_id=" + group_id +
                ", title='" + title + '\'' +
                ", todoDate='" + todoDate + '\'' +
                ", todoTime='" + todoTime + '\'' +
                ", isCheck='" + isCheck + '\'' +
                ", isOpen=" + isOpen +
                ", isLevel=" + isLevel +
                ", groupName='" + groupName + '\'' +
                ", groupColor='" + groupColor + '\'' +
                '}';
    }
}
