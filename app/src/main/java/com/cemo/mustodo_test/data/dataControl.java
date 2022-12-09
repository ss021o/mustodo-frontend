package com.cemo.mustodo_test.data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class dataControl {
    dataHelper helper;
    SQLiteDatabase sqlite;

    public dataControl(dataHelper _helper){
        this.helper = _helper;
    }

    public void insert(int _id, String _email, String _nickname, String _msg, String _profile){
        sqlite = helper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(helper.user_id, _id);
        values.put(helper.email, _email);
        values.put(helper.nickname, _nickname);
        values.put(helper.msg, _msg);
        values.put(helper.profile, _profile);

        sqlite.insert(helper.TABLE_NAME, null, values);
    }

    @SuppressLint("Range")
    public String[] select(){
        sqlite = helper.getReadableDatabase();
        // 커서 사용
        Cursor c = sqlite.query(helper.TABLE_NAME, null, null, null,null,null,null);

        // 칼럼 정보를 배열에 넣고
        String[] columnName = {helper.user_id, helper.email, helper.nickname, helper.msg, helper.profile};

        // 칼럼 정보와 길이가 같은 배열을 생성 후
        String[] returnValue = new String[columnName.length];

        // 생성한 배열에 데이터를 받아줍니다.
        while(c.moveToNext()){
            for(int i=0 ; i<returnValue.length; i++){
                returnValue[i] = c.getString(c.getColumnIndex(columnName[i]));
                Log.e("DB Select : ",i + " - "+returnValue[i]);
            }
        }
        // 커서를 사용 후에 꼭 닫아줍시다!
        c.close();
        return returnValue;
    }

    public void update(String _key, String _value, String _email){
        sqlite = helper.getWritableDatabase();

        ContentValues value = new ContentValues();
        value.put(_key, _value);
        // Primary Key
        sqlite.update(helper.TABLE_NAME, value, "email=?", new String[]{_email});
    }

    public void delete(String _email){
        sqlite = helper.getWritableDatabase();
        // Primary Key
        sqlite.delete(helper.TABLE_NAME, "email=?", new String[]{_email});
    }

    public void db_close(){
        sqlite.close();
        helper.close();
    }


}
