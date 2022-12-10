package com.cemo.mustodo_test.todo;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cemo.mustodo_test.R;

import java.util.ArrayList;

public class OpenTodoAdaper extends BaseAdapter {
    Context mContext;
    LayoutInflater mLayoutInflater;
    ArrayList<TodoData> items = new ArrayList<TodoData>();

    public OpenTodoAdaper(Context context, ArrayList<TodoData> data) {
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
    public TodoData getItem(int position) {
        return items.get(position);
    }

    public void addItem(TodoData item){
        items.add(item);
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.lv_todo_open, null);

        TodoData listViewItem = items.get(position);

        TextView userNick = (TextView) view.findViewById(R.id.open_user_nick);
        TextView userMsg = (TextView) view.findViewById(R.id.open_profile_msg);
        ImageView userProfile = (ImageView) view.findViewById(R.id.open_profile_view);

        CheckBox todo_check = (CheckBox) view.findViewById(R.id.open_todo_check);
        TextView todo_text = (TextView) view.findViewById(R.id.open_todo_text);
        TextView todo_date = (TextView) view.findViewById(R.id.open_todo_date);


        userNick.setText(items.get(position).getUserNick());

        if(items.get(position).getUserProfile().equals("noImg")){
            userProfile.setImageResource(R.drawable.noimg);
        }else{
            String imageUrl = "https://aws-tiqets-cdn.imgix.net/images/content/f02865ee82a44cf0a87e9f72f7258fa1.jpg?auto=format&fit=crop&ixlib=python-3.2.1&q=70&s=e70b1d80f5538f189ea48bea0c48e079";
            Glide.with(mContext).load(imageUrl).into(userProfile);
        }

        userProfile.setClipToOutline(true);


        todo_check.setChecked(items.get(position).getTodoCheck());
        todo_text.setText(items.get(position).getTitle());
        todo_date.setText(items.get(position).getTodoDate());



        return view;
    }
}