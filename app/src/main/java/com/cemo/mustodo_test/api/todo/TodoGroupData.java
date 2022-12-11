package com.cemo.mustodo_test.api.todo;

import com.google.gson.annotations.SerializedName;

public class TodoGroupData {
    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("color")
    private String color;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getColor() {
        return color;
    }

    public String getTitle() {
        return title;
    }

    public TodoGroupData(String title, String color){
        this.color = color;
        this.title = title;
    }

    public TodoGroupData(String title, int id, String color){
        this.color = color;
        this.id = id;
        this.title = title;
    }
}
