package com.cemo.mustodo_test.todo.recyclerlist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cemo.mustodo_test.R;
import com.cemo.mustodo_test.todo.TodoData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TodoRecyclerAdapter extends RecyclerView.Adapter<TodoRecyclerAdapter.ViewHolder> {
    Context context;
    List<TodoListMap> items;

    @SuppressLint("NewApi")
    public TodoRecyclerAdapter(List<TodoData> items) {
        Map<TodoGroupKey, List<TodoGroupValue>> todoMap = new HashMap<>();

        for (TodoData item : items) {
            TodoGroupKey key = new TodoGroupKey(item.getGroupName(), item.getGroupColor());
            TodoGroupValue value = new TodoGroupValue(item.getTitle(), item.getTodoCheck(), item.getTodoDate());
            todoMap.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
        }
        this.items = todoMap.entrySet().stream()
                .map(entry -> new TodoListMap(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_lvcategoryitem, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        final TodoListMap todoListMap = items.get(i);

        initGroup(holder, todoListMap);

        LinearLayout todoLayout = holder.todoLayout;
        System.out.println("todoLayout = " + todoLayout);
        for (TodoGroupValue value : todoListMap.getValue()) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_lvtodoitem, null, false);

            CheckBox check = view.findViewById(R.id.todo_check);
            TextView content = view.findViewById(R.id.todo_text);
            TextView date = view.findViewById(R.id.todo_date);

            check.setChecked(value.isCheck());
            content.setText(value.getTitle());
            date.setText(value.getTodoDate());

            view.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v){
                    //해당 리스트 클릭시 이벤트
                    Toast.makeText(v.getContext(), value.getTitle()  + " : " + value.isCheck() , Toast.LENGTH_SHORT).show();

                    if(value.isCheck()){
                        check.setChecked(false);
                        value.setCheck(false);
                        content.setPaintFlags(content.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                        date.setPaintFlags(date.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                    }else{
                        check.setChecked(true);
                        value.setCheck(true);
                        content.setPaintFlags(content.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        date.setPaintFlags(date.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    }
                    /*TODO
                    서버에 checked 보내기
                     */
                }
            });

            check.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(value.isCheck()){
                        check.setChecked(false);
                        content.setPaintFlags(content.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                        date.setPaintFlags(date.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                    }else{
                        check.setChecked(true);
                        content.setPaintFlags(content.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        date.setPaintFlags(date.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    }
                }
            });
            todoLayout.addView(view);
        }
    }

    private void addView(View view, LinearLayout linearLayout) {
        if(view.getParent() != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
        linearLayout.addView(view);
    }

    private void initGroup(@NonNull ViewHolder holder, TodoListMap todoListMap) {
        Button groupName = holder.todoGroup.findViewById(R.id.groupName);
        groupName.setText(todoListMap.getKey().getGroupName());
        LinearLayout groupColor = holder.todoGroup.findViewById(R.id.groupColor);
        groupColor.setBackgroundColor(Color.parseColor(todoListMap.getKey().getGroupColor()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout todoGroup;
        LinearLayout todoLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            todoGroup = itemView.findViewById(R.id.todoGroup);
            todoLayout = itemView.findViewById(R.id.todoItemLayout);
        }
    }
}