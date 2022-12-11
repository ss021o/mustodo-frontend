package com.cemo.mustodo_test.todo;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cemo.mustodo_test.R;

import java.util.ArrayList;

public class RepeatAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater mLayoutInflater;
    ArrayList<String> items = new ArrayList<String>();

    public RepeatAdapter(Context context, ArrayList<String> data) {
        mContext = context;
        items = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public String  getItem(int position) {
        return items.get(position);
    }


    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.lv_todo_sheet_list, null);


        TextView text_sheet_item = (TextView) view.findViewById(R.id.text_sheet_item);

        text_sheet_item.setText(items.get(position).toString());

        return view;
    }
}
