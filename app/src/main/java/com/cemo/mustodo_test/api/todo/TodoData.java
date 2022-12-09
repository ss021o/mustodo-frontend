package com.cemo.mustodo_test.api.todo;

import com.google.gson.annotations.SerializedName;

public class TodoData {
    @SerializedName("id")
    private String todo_cate;
    private String todo_color;

    @SerializedName("title")
    private String todo_title;

    @SerializedName("todoDate")
    private String todo_date;

    @SerializedName("todoTime")
    private String todo_time;

    @SerializedName("isOpen")
    private Boolean isOpen;

    @SerializedName("isCheck")
    private Boolean isCheck;

    @SerializedName("isLevel")
    private int isLevel;

    private int id;

    @SerializedName("group_id")
    private int group_id;

    public TodoData(int id, String todo_cate, String todo_color, String todo_title, String todo_date, String todo_time, Boolean isLevel, Boolean isCheck)
    {
        this.id = id;
        this.todo_cate = todo_cate;
        this.todo_color = todo_color;
        this.todo_date = todo_date;
        this.todo_time = todo_time;
        this.todo_title = todo_title;
        this.isCheck = isCheck;
        this.isOpen = isOpen;
    }


    public TodoData(int group_id, String title, String todoDate, String todoTime, Boolean isOpen, int isLevel)
    {
        this.group_id = group_id;
        this.todo_title = title;
        this.todo_date =todoDate;
        this.todo_time = todoTime;
        this.isOpen = isOpen;
        this.isLevel = isLevel;
    }



    public TodoData(int id, String todo_cate, String todo_color, String todo_title, String todo_date, String todo_time, Boolean isOpen, int isLevel, Boolean isCheck)
    {
        this.id = id;
        this.todo_cate = todo_cate;
        this.todo_color =todo_color;
        this.todo_date = todo_date;
        this.todo_time = todo_time;
        this.todo_title = todo_title;
        this.isCheck = isCheck;
        this.isOpen = isOpen;
        this.isLevel = isLevel;
    }

    //private String todo_text;
    //private String todo_date;
    //private boolean todo_check;

//    public TodoData(boolean todo_check, String todo_text, String todo_date){
//        this.todo_text = todo_text;
//        this.todo_date = todo_date;
//        this.todo_check = todo_check;
//    }
//
//    public String getTodoText()
//    {
//        return this.todo_text;
//    }
//
//    public String getTodoDate()
//    {
//        return this.todo_date;
//    }
//
//    public boolean getTodoCheck()
//    {
//        return this.todo_check;
//    }
}
