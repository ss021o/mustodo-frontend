package com.cemo.mustodo_test.feed.todo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
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

import com.bumptech.glide.Glide;
import com.cemo.mustodo_test.R;
import com.cemo.mustodo_test.api.RetrofitClient;
import com.cemo.mustodo_test.api.todo.TodoServiceInterface;
import com.cemo.mustodo_test.todo.TodoData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import de.hdodenhof.circleimageview.CircleImageView;

public class TodoFeedRecyclerAdapter extends RecyclerView.Adapter<TodoFeedRecyclerAdapter.ViewHolder> {
    Context context;
    List<TodoFeedListMap> items;
    private TodoServiceInterface todoService;

    @SuppressLint("NewApi")
    public TodoFeedRecyclerAdapter(List<TodoData> items) {
        todoService = RetrofitClient.getClient().create(TodoServiceInterface.class);
        Map<TodoFeedGroupKey, List<TodoFeedGroupValue>> todoMap = new HashMap<>();

        for (TodoData item : items) {
            TodoFeedGroupKey key = new TodoFeedGroupKey(item.getUserNick(), item.getUserProfile(), item.getUserMsg());
            TodoFeedGroupValue value = new TodoFeedGroupValue(item.getTitle(), item.getGroupColor());
            todoMap.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
        }
        this.items = todoMap.entrySet().stream()
                .map(entry -> new TodoFeedListMap(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_lvfeeduser, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        final TodoFeedListMap todoListMap = items.get(i);

        initGroup(holder, todoListMap.getKey());

        LinearLayout todoLayout = holder.todoLayout;
        for (TodoFeedGroupValue value : todoListMap.getValue()) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_lvfeedtodo, null, false);

            CircleImageView check = view.findViewById(R.id.check);
            String color = value.getGroupColor();
            if (color.startsWith("#")) {
                int parseColor = Color.parseColor(color);
                check.setCircleBackgroundColor(parseColor);
            } else {
                check.setCircleBackgroundColor(Integer.parseInt(color));
            }

            TextView content = view.findViewById(R.id.content);
            content.setText(value.getTitle());

            todoLayout.addView(view);
        }
    }

    private void initGroup(@NonNull ViewHolder holder, TodoFeedGroupKey user) {
        LinearLayout userLayout = holder.userLayout;

        TextView userName = userLayout.findViewById(R.id.userName);
        userName.setText(user.getUserName());

        CircleImageView profile = userLayout.findViewById(R.id.profile);
        String profilePath = user.getProfile();
        if (!"noImg".equals(profilePath)) {
            Uri profileUri = Uri.parse(profilePath);
            Glide.with(userLayout).load(profileUri).into(profile);
        } else {
            String imageUrl = "https://aws-tiqets-cdn.imgix.net/images/content/f02865ee82a44cf0a87e9f72f7258fa1.jpg?auto=format&fit=crop&ixlib=python-3.2.1&q=70&s=e70b1d80f5538f189ea48bea0c48e079";
            Glide.with(userLayout).load(imageUrl).into(profile);
        }

        TextView userMsg = userLayout.findViewById(R.id.userMsg);
        userMsg.setText(user.getUserMsg());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout userLayout;
        LinearLayout todoLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userLayout = itemView.findViewById(R.id.todoUserLayout);
            todoLayout = itemView.findViewById(R.id.todoList);
        }
    }
}