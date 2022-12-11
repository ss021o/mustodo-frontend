package com.cemo.mustodo_test.todo.recyclerlist;

public class TodoGroupValue {
    private final int todoId;
    private final String title;
    private boolean isCheck;
    private final String todoDate;

    public TodoGroupValue(int todoId, String title, boolean isCheck, String todoDate) {
        this.todoId = todoId;
        this.title = title;
        this.isCheck = isCheck;
        this.todoDate = todoDate;
    }

    public int getTodoId() {
        return todoId;
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
