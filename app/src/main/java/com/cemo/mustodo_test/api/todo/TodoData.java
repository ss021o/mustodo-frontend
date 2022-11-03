package com.cemo.mustodo_test.api.todo;

public class TodoData {
    private String todo_text;
    private String todo_date;
    private boolean todo_check;

    public TodoData(boolean todo_check, String todo_text, String todo_date){
        this.todo_text = todo_text;
        this.todo_date = todo_date;
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
}
