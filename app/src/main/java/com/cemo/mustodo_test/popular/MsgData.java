package com.cemo.mustodo_test.popular;

import com.google.gson.annotations.SerializedName;

public class MsgData {

    @SerializedName("id")
    private int id;

    @SerializedName("contents")
    private String contents;

    @SerializedName("talker")
    private String talker;

    @SerializedName("img")
    private String img;

    @SerializedName("liked")
    private int like;

    public MsgData(int id, String contents, String talker,  int like){
        this.id = id;
        this.contents = contents;
        this.talker = talker;
        this.like = like;
    }


    public MsgData(String contents, String talker, String img, int like){
        this.contents = contents;
        this.talker = talker;
        this.img = img;
        this.like = like;
    }

    public int getId() { return id; }

    public String getContents() { return contents; }

    public String getImg() { return img; }

    public int getLike() { return like; }

    public String getTalker() { return talker; }

    public void setImg(String img) { this.img = img; }

    public void setContents(String contents) { this.contents = contents; }

    public void setLike(int like) { this.like = like; }

    public void setTalker(String talker) { this.talker = talker; }
}