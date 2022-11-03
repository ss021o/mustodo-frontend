package com.cemo.mustodo_test;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.cemo.mustodo_test.api.todo.CategoryTodoResponse;
import com.cemo.mustodo_test.api.todo.TodoData;
import com.cemo.mustodo_test.api.todo.TodoResponse;

import java.util.List;
import java.util.Map;

public class TodoAdapter extends BaseExpandableListAdapter {
    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    List<CategoryTodoResponse> categoryTodoList;

    public TodoAdapter(Context context, List<CategoryTodoResponse> data) {
        mContext = context;
        categoryTodoList = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getGroupCount() {
        return categoryTodoList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return categoryTodoList.get(groupPosition).getTodolist().size();
    }

    @Override
    public CategoryTodoResponse getGroup(int groupPosition) {
        return categoryTodoList.get(groupPosition);
    }

    @Override
    public TodoResponse getChild(int groupPosition, int childPosition) {
        return categoryTodoList.get(groupPosition).getTodolist().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view = convertView;
        Context context = parent.getContext();

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_lvcategoryitem, parent, false);
        }

        TextView textView = view.findViewById(R.id.category);

        textView.setText(categoryTodoList.get(groupPosition).getCategoryName());
        textView.setBackgroundColor(Color.parseColor(categoryTodoList.get(groupPosition).getColor()));
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = convertView;
        Context context = parent.getContext();

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_lvtodoitem, parent, false);
        }

        CheckBox todo_check = (CheckBox) view.findViewById(R.id.todo_check);
        TextView todo_text = (TextView) view.findViewById(R.id.todo_text);
        TextView todo_date = (TextView) view.findViewById(R.id.todo_date);

        todo_check.setChecked(categoryTodoList.get(groupPosition).getTodolist().get(childPosition).isCheck());
        todo_text.setText(categoryTodoList.get(groupPosition).getTodolist().get(childPosition).getContent());
//        todo_date.setText(categoryTodoList.get(groupPosition).getTodolist().get(childPosition).getTodoDate());

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
