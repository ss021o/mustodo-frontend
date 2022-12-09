package com.cemo.mustodo_test.api.todo;

import com.google.gson.annotations.SerializedName;

public class TodoMonthData {
    @SerializedName("todoDate")
    private String todoDate;

    @SerializedName("Count")
    private int Count;

    public int getCount(){
        return Count;
    }

    public String getTodoDate(){
        return todoDate;
    }

    public TodoMonthData(String todoDate, int Count) {
        this.todoDate = todoDate;
        this.Count = Count;
    }

}
