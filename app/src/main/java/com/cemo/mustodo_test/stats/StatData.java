package com.cemo.mustodo_test.stats;

import com.google.gson.annotations.SerializedName;

public class StatData {

    @SerializedName("todoDate")
    private String todoDate;

    @SerializedName("total")
    private int total;

    @SerializedName("checked")
    private int checked;

    @SerializedName("notfinish")
    private int notfinish;

    @SerializedName("notchecked")
    private int notchecked;

    public int getTotal() { return this.total; }

    public String getTodoDate() { return this.todoDate; }

    public int getChecked() { return this.checked; }

    public int getNotchecked() { return this.notchecked; }

    public int getNotfinish() { return this.notfinish; }

    public StatData(String todoDate, int total){
        this.todoDate = todoDate;
        this.total = total;
    }

    public StatData(int checked, int notfinish, int notchecked){
        this.checked = checked;
        this.notfinish = notfinish;
        this.notchecked = notchecked;
    }
}
