package com.cemo.mustodo_test.stats;

import com.google.gson.annotations.SerializedName;

public class StatMonthData {

    @SerializedName("total")
    private int total;

    @SerializedName("checked")
    private int checked;

    @SerializedName("todoDate")
    private String todoDate;

    public int getDaysChecked() {
        return this.checked;
    }

    public int getDaysTotal() {
        return this.total;
    }

    public String getDays() {
        return this.todoDate;
    }

    public void setChecked(int checked) { this.checked = checked; }

    public void setTodoDate(String todoDate) { this.todoDate = todoDate; }

    public void setTotal(int total) { this.total = total; }

    public StatMonthData(String todoDate, int total ){
        this.todoDate = todoDate;
        this.total = total;
    }

    public StatMonthData(int checked, String todoDate ){
        this.todoDate = todoDate;
        this.checked = checked;
    }

    public StatMonthData(String todoDate, int total, int checked ){
        this.todoDate = todoDate;
        this.total = total;
        this.checked = checked;
    }
}
