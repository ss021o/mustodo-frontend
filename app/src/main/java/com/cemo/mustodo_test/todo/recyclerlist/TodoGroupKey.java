package com.cemo.mustodo_test.todo.recyclerlist;

import java.util.Objects;

public class TodoGroupKey {
    private final String groupName;
    private final String groupColor;

    public TodoGroupKey(String groupName, String groupColor) {
        this.groupName = groupName;
        this.groupColor = groupColor;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getGroupColor() {
        return groupColor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TodoGroupKey that = (TodoGroupKey) o;
        return Objects.equals(groupName, that.groupName) && Objects.equals(groupColor, that.groupColor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupName, groupColor);
    }
}
