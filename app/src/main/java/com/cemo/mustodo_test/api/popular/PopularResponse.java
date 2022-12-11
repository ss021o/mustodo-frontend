package com.cemo.mustodo_test.api.popular;

import com.cemo.mustodo_test.popular.MsgData;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PopularResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("data")
    private List<MsgData> data;

    public List<MsgData> getData() {
        return  data;
    }

    public int getCode() {
        return code;
    }
}
