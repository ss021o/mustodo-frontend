package com.cemo.mustodo_test.feed.todo;

import java.util.Objects;

public class TodoFeedGroupKey {
    private final String userName;
    private final String profile;
    private final String userMsg;

    public TodoFeedGroupKey(String userName, String profile, String userMsg) {
        this.userName = userName;
        this.profile = profile;
        this.userMsg = userMsg;
    }

    public String getUserName() {
        return userName;
    }

    public String getProfile() {
        return profile;
    }

    public String getUserMsg() {
        return userMsg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TodoFeedGroupKey that = (TodoFeedGroupKey) o;
        return Objects.equals(userName, that.userName) && Objects.equals(profile, that.profile) && Objects.equals(userMsg, that.userMsg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, profile, userMsg);
    }
}
