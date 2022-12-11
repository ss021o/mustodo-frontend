package com.cemo.mustodo_test.api.todo;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryTodoResponse {

    @SerializedName("categoryId")
    private Long categoryId;

    @SerializedName("color")
    private String color;

    @SerializedName("categoryName")
    private String categoryName;

    @SerializedName("todoList")
    private List<TodoResponse> todolist;

    public Long getCategoryId() {
        return categoryId;
    }

    public String getColor() {
        return color;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public List<TodoResponse> getTodolist() {
        return todolist;
    }

    @NonNull
    @Override
    public String toString() {
        return "categoryName: " + categoryName +
                "\ntodoListSize: " + todolist.size();
    }
}
