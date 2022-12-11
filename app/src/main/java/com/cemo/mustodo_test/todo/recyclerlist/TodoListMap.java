package com.cemo.mustodo_test.todo.recyclerlist;

import java.util.List;

public class TodoListMap {
    private final TodoGroupKey key;
    private final List<TodoGroupValue> value;

    public TodoListMap(TodoGroupKey key, List<TodoGroupValue> value) {
        this.key = key;
        this.value = value;
    }

    public TodoGroupKey getKey() {
        return key;
    }

    public List<TodoGroupValue> getValue() {
        return value;
    }
}
