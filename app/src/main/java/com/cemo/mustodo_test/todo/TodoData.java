package com.cemo.mustodo_test.todo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Date;
import java.sql.Time;

public class TodoData {
    @SerializedName("nickname")
    @Expose
    private String nickname;

    @SerializedName("profile")
    @Expose
    private String profile;

    @SerializedName("mymsg")
    @Expose
    private String mymsg;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("todoDate")
    @Expose
    private String todo_date;

    @SerializedName("isCheck")
    @Expose
    private boolean todo_check;

    @SerializedName("todoTime")
    @Expose
    private String todo_time;

    @SerializedName("cate")
    @Expose
    private String groupName;

    @SerializedName("color")
    @Expose
    private String groupColor;


    private Boolean isOpen;
    private Boolean check;
    private int isLevel;

    @SerializedName("id")
    @Expose
    private int id;
    private int group_id;


    public int getId() {
        return id;
    }

    public String getTodoTime(){
            return this.todo_time;
    }

    public int getGroupId(){
        return this.group_id;
    }
    public String getUserNick() {
        return this.nickname;
    }

    public String getUserMsg() {
        return this.mymsg;
    }

    public String getUserProfile() {
        return this.profile;
    }

    public String getTitle()
    {
        return this.title;
    }

    public String getTodoDate()
    {
        return this.todo_date;
    }

    public boolean getTodoCheck()
    {
        return this.todo_check;
    }

    public void setTodoDate(String date){
        this.todo_date = date;
    }

    public void setTodoTime(String time){
        this.todo_time = time;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setUserMsg(String userMsg) {
        this.mymsg = userMsg;
    }

    public void setUserNick(String userNick) {
        this.nickname = userNick;
    }

    public void setUserProfile(String userProfile) {
        this.profile = userProfile;
    }

    public boolean setTodoCheck(boolean checked)
    {
        todo_check = checked;
        return this.todo_check;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getGroupColor() {
        return groupColor;
    }

//     "id": 5,
//     "user_id": 1,
//     "group_id": 1,
//     "title": "PT(상체)",
//     "todoDate": "2022-12-05T15:00:00.000Z",
//     "todoTime": "21:00:00",
//     "created": 1670428901,
//     "updated": 1670428901,
//     "isCheck": "0",
//     "isOpen": "1",
//     "isLevel": "5",
//     "nickname": "testUser",
//     "profile": "",
//     "mymsg": ""


    public TodoData(int id, String nickname, String profile, String mymsg, int group_id, String title, String todoDate, String todoTime, Boolean todo_check, int isLevel)
    {
        this.id = id;
        this.nickname = nickname;
        this.profile =profile;
        this.mymsg = mymsg;
        this.group_id = group_id;
        this.title = title;
        this.todo_date =todoDate;
        this.todo_time = todoTime;
        this.todo_check = todo_check;
        this.isLevel = isLevel;
    }

    public TodoData(int id, boolean todo_check, String title, String todo_date, String todo_time, String groupName, String groupColor){
        this.id = id;
        this.todo_date = todo_date;
        this.todo_time = todo_time;
        this.title = title;
        this.todo_check = todo_check;
        this.groupName = groupName;
        this.groupColor = groupColor;
    }

    public TodoData(String userName, String title, String mymsg, String profile, String groupColor){
        this.nickname = userName;
        this.title = title;
        this.mymsg = mymsg;
        this.profile = profile;
        this.groupColor = groupColor;
    }
}