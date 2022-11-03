package com.cemo.mustodo_test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class TodoAdapter extends BaseAdapter {
    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<TodoData> sample;

    public TodoAdapter(Context context, ArrayList<TodoData> data) {
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
    public TodoData getItem(int position) {
        return sample.get(position);
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.item_lvtodoitem, null);

        CheckBox todo_check = (CheckBox) view.findViewById(R.id.todo_check);
        TextView todo_text = (TextView) view.findViewById(R.id.todo_text);
        TextView todo_date = (TextView) view.findViewById(R.id.todo_date);

        todo_check.setChecked(sample.get(position).getTodoCheck());
        todo_text.setText(sample.get(position).getTodoText());
        todo_date.setText(sample.get(position).getTodoDate());

        return view;
    }
}
