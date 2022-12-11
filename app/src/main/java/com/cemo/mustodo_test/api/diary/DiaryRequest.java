package com.cemo.mustodo_test.api.diary;

import java.time.LocalDate;

public class DiaryRequest {
    private String title;
    private String content;
    private boolean isOpen;
    private String date;

    public DiaryRequest(String title, String content, boolean isOpen) {
        this.title = title;
        this.content = content;
        this.isOpen = isOpen;
        this.date = LocalDate.now().toString();
    }
}
