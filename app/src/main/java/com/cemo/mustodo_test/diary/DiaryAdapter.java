package com.cemo.mustodo_test.diary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.cemo.mustodo_test.R;
import com.cemo.mustodo_test.todo.TodoData;

import java.util.ArrayList;
import java.util.List;

public class DiaryAdapter extends BaseAdapter {
    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    List<DiaryData> sample;

    public DiaryAdapter(Context context, List<DiaryData> data) {
        mContext = context;
        sample = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return sample.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public DiaryData getItem(int position) {
        return sample.get(position);
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_lvdiaryitem, null, false);

        TextView diary_title = (TextView) view.findViewById(R.id.diary_title);
        TextView diary_contents = (TextView) view.findViewById(R.id.diary_contents);

        diary_title.setText(sample.get(position).getDiaryTitle());
        diary_contents.setText(sample.get(position).getDiaryContents());

        return view;
    }
}