package com.cemo.mustodo_test.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

public class dataHelper extends android.database.sqlite.SQLiteOpenHelper{

    public final String TABLE_NAME = "mustodo_user";
    public final String user_id = "user_id" ;
    public final String email = "email";
    public final String nickname = "nickname";
    public final String msg = "msg";
    public final String profile = "profile";

    public dataHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_query = "create table if not exists " + TABLE_NAME + "("
                + user_id + " int not null , "
                + email + " text not null primary key , "
                + nickname + " text not null  , "
                + msg + " text , "
                + profile + " text  );"; // primary key는 이렇게 선언합니다.

        // 위 Create Query로 Table을 생성해줍니다.
        db.execSQL(create_query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String drop_query = "drop table " + TABLE_NAME + ";";
        db.execSQL(drop_query);

        // onCreate를 호출해서 Table 다시 생성
        onCreate(db);
    }
}
