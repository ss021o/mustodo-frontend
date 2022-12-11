package com.cemo.mustodo_test.todo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.cemo.mustodo_test.R;
import com.cemo.mustodo_test.api.todo.TodoGroupData;

import java.util.ArrayList;

public class ProjectAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater mLayoutInflater;
    ArrayList<TodoGroupData> items = new ArrayList<TodoGroupData>();

    public ProjectAdapter(Context context, ArrayList<TodoGroupData> data ) {
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
    public TodoGroupData getItem(int position) {
        return items.get(position);
    }

    public void addItem(TodoGroupData item){
        items.add(item);
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.lv_todo_sheet_list, null);

        final GradientDrawable drawable = (GradientDrawable) ContextCompat.getDrawable(mContext, R.drawable.btn_gray);


        TodoGroupData listViewItem = items.get(position);

        Button color_pro =view.findViewById(R.id.color_pro);
        TextView text_sheet_item = (TextView) view.findViewById(R.id.text_sheet_item);

        text_sheet_item.setText(items.get(position).getTitle());

        if(items.get(position).getColor().contains("#")){
            drawable.setColor(Color.parseColor(items.get(position).getColor()));
        }else {
            drawable.setColor(Integer.parseInt(items.get(position).getColor()));
        }

        color_pro.setBackground(drawable);


        LinearLayout clickLayout= (LinearLayout)view.findViewById(R.id.btItemsLayout);

        clickLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                //해당 리스트 클릭시 이벤트
                Intent intent = new Intent("select_project");
                //            intent.putExtra("quantity",Integer.parseInt(quantity.getText().toString()));

                System.out.println(items.get(position).getId());
                intent.putExtra("title",items.get(position).getTitle());
                intent.putExtra("group_id",items.get(position).getId());
                intent.putExtra("color",items.get(position).getColor());
                LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
            }
        });

        return view;
    }
}