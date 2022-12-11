package com.cemo.mustodo_test.diary;

import java.sql.Date;
import java.sql.Time;

public class DiaryData {
    private String title;
    private String contents;
    private String imgUrl;
    private String date;

    public DiaryData(String title, String contents, String imgUrl, String date){
        this.title = title;
        this.contents = contents;
        this.imgUrl = imgUrl;
        this.date = date;
    }

    public String getDiaryTitle()
    {
        return this.title;
    }
    public String getDiaryContents()
    {
        return this.contents;
    }
    public String getDiaryImg()
    {
        return this.imgUrl;
    }
    public String getDiaryDate()
    {
        return this.date;
    }

}