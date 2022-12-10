package com.cemo.mustodo_test.popular;

public class MsgData {
    private String title;
    private String msg;
    private String imgUrl;
    private String date;
    private int like;
    private int comment;

    public MsgData(String title, String contents, String imgUrl, String date, int like, int comment){
        this.title = title;
        this.imgUrl = imgUrl;
        this.date = date;
        this.like = like;
        this.comment = comment;
    }

    public String getDiaryTitle()
    {
        return this.title;
    }
    public String getDiaryImg()
    {
        return this.imgUrl;
    }
    public String getDiaryDate()
    {
        return this.date;
    }
    public int getDiaryLike()
    {
        return this.like;
    }
    public int getDiaryComment()
    {
        return this.comment;
    }

}