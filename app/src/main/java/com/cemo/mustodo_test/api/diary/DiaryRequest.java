package com.cemo.mustodo_test.api.diary;

public class DiaryRequest {
    private String title;
    private String content;
    private boolean isOpen;

    public DiaryRequest(String title, String content, boolean isOpen) {
        this.title = title;
        this.content = content;
        this.isOpen = isOpen;
    }
}
