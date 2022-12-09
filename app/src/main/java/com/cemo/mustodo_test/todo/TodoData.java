package com.cemo.mustodo_test.todo;

import java.sql.Date;
import java.sql.Time;

public class TodoData {
    private String todo_text;
    private String todo_date;
    private boolean todo_check;

    private String todo_time;

    public TodoData(boolean todo_check, String todo_text, String todo_date, String todo_time){
        this.todo_date = todo_date;
        this.todo_time = todo_time;
        this.todo_text = todo_text;
        this.todo_check = todo_check;
    }

    public String getTodoText()
    {
        return this.todo_text;
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

    public void setTodoTitle(String title){
        this.todo_text = title;
    }

    public boolean setTodoCheck(boolean checked)
    {
        todo_check = checked;
        return this.todo_check;
    }
}