package com.cemo.mustodo_test.todo;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cemo.mustodo_test.R;

import java.util.ArrayList;

public class TodoAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater mLayoutInflater;
    ArrayList<TodoData> items = new ArrayList<TodoData>();

    public TodoAdapter(Context context, ArrayList<TodoData> data) {
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
        View view = mLayoutInflater.inflate(R.layout.item_lvtodoitem, null);

        TodoData listViewItem = items.get(position);

        CheckBox todo_check = (CheckBox) view.findViewById(R.id.todo_check);
        TextView todo_text = (TextView) view.findViewById(R.id.todo_text);
        TextView todo_date = (TextView) view.findViewById(R.id.todo_date);

        todo_check.setChecked(items.get(position).getTodoCheck());
        todo_text.setText(items.get(position).getTodoText());
        todo_date.setText(items.get(position).getTodoDate());

        LinearLayout clickLayout= (LinearLayout)view.findViewById(R.id.todoItemLayout);

        clickLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                //해당 리스트 클릭시 이벤트
                Toast.makeText(v.getContext(), items.get(position).getTodoText()  + " : " + items.get(position).getTodoCheck() , Toast.LENGTH_SHORT).show();

                //sample.get(position).getTodoCheck() = true;
                if(items.get(position).getTodoCheck()){
                    todo_check.setChecked(false);
                    items.get(position).setTodoCheck(false);
                    todo_text.setPaintFlags(todo_text.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                    todo_date.setPaintFlags(todo_text.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                }else{
                    todo_check.setChecked(true);
                    items.get(position).setTodoCheck(true);
                    todo_text.setPaintFlags(todo_text.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    todo_date.setPaintFlags(todo_text.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }

            }
        });

        todo_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(items.get(position).getTodoCheck()){
                    todo_check.setChecked(false);
                    todo_text.setPaintFlags(todo_text.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                    todo_date.setPaintFlags(todo_text.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                }else{
                    todo_check.setChecked(true);
                    todo_text.setPaintFlags(todo_text.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    todo_date.setPaintFlags(todo_text.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }
            }
        });

        return view;
    }
}