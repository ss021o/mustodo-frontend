package com.cemo.mustodo_test.feed.todo;

public class TodoFeedGroupValue {
    private final String title;
    private final String groupColor;

    public TodoFeedGroupValue(String title, String groupColor) {
        this.title = title;
        this.groupColor = groupColor;
    }

    public String getTitle() {
        return title;
    }

    public String getGroupColor() {
        return groupColor;
    }
}
