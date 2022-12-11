package com.cemo.mustodo_test.api;

import com.google.gson.annotations.SerializedName;

public class GoalData {
    @SerializedName("mymsg")
    private String mymsg;

    public GoalData(String mymsg) {
        this.mymsg = mymsg;
    }
}
