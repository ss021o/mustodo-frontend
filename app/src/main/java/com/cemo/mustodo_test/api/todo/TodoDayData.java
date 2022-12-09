package com.cemo.mustodo_test.api.todo;

import com.google.gson.annotations.SerializedName;


public class TodoDayData {
    @SerializedName("id")
    private int id;

    @SerializedName("user_id")
    private int user_id;

    @SerializedName("group_id")
    private int group_id;

    @SerializedName("title")
    private String title;

    @SerializedName("todoDate")
    private String todoDate;

    @SerializedName("todoTime")
    private String todoTime;

    @SerializedName("isCheck")
    private Boolean isCheck;

    @SerializedName("isOpen")
    private Boolean isOpen;

    @SerializedName("isLevel")
    private int isLevel;

    @SerializedName("cate")
    private String cate;

    @SerializedName("color")
    private String color;

    public String getTodoTitle(){
        return title;
    }

    public Boolean getIsCheck(){
        return isCheck;
    }

    public Boolean getIsOpen(){
        return isOpen;
    }

    public String getTodoDate(){
        return todoDate;
    }

    public String getTodoTime(){
        return todoTime;
    }

    public String getTodoColor(){
        return color;
    }


    public TodoDayData(int id, int user_id, int group_id, String title, String todoDate, String todoTime, Boolean isCheck, Boolean isOpen, int isLevel, String cate, String color) {
       this.id = id;
       this.user_id = user_id;
       this.group_id = group_id;
       this.title = title;
       this.todoDate = todoDate;
       this.todoTime = todoTime;
       this.isCheck = isCheck;
       this.isOpen = isOpen;
       this.isLevel =isLevel;
       this.cate = cate;
       this.color = color;
    }
}
