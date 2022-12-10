package com.cemo.mustodo_test.popular;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cemo.mustodo_test.R;
import com.cemo.mustodo_test.diary.DiaryData;

import java.util.ArrayList;

public class MsgAdapter extends BaseAdapter {
    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<DiaryData> sample;

    public MsgAdapter(Context context, ArrayList<DiaryData> data) {
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
        View view = mLayoutInflater.inflate(R.layout.item_lvdiaryitem, null);

        TextView diary_title = (TextView) view.findViewById(R.id.diary_title);
        TextView diary_contents = (TextView) view.findViewById(R.id.diary_contents);
        TextView dlike_counts = (TextView) view.findViewById(R.id.dlike_counts);
        TextView dcomment_counts = (TextView) view.findViewById(R.id.dcomment_counts);

        diary_title.setText(sample.get(position).getDiaryTitle());
        diary_contents.setText(sample.get(position).getDiaryContents());
        dlike_counts.setText(String.valueOf(sample.get(position).getDiaryLike()) );
        dcomment_counts.setText(String.valueOf(sample.get(position).getDiaryComment()) );

        return view;
    }
}