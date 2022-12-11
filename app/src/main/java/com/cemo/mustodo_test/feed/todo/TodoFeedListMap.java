package com.cemo.mustodo_test.feed.todo;

import java.util.List;

public class TodoFeedListMap {
    private final TodoFeedGroupKey key;
    private final List<TodoFeedGroupValue> value;

    public TodoFeedListMap(TodoFeedGroupKey key, List<TodoFeedGroupValue> value) {
        this.key = key;
        this.value = value;
    }

    public TodoFeedGroupKey getKey() {
        return key;
    }

    public List<TodoFeedGroupValue> getValue() {
        return value;
    }
}
