package com.cemo.mustodo_test.todo.recyclerlist;

public class TodoGroupValue {
    private final String title;
    private boolean isCheck;
    private final String todoDate;

    public TodoGroupValue(String title, boolean isCheck, String todoDate) {
        this.title = title;
        this.isCheck = isCheck;
        this.todoDate = todoDate;
    }

    public String getTitle() {
        return title;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public String getTodoDate() {
        return todoDate;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
