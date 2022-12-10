package com.cemo.mustodo_test.feed;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cemo.mustodo_test.R;
import com.cemo.mustodo_test.api.sns.TodoFeedDto;
import com.cemo.mustodo_test.api.sns.TodoFeedValue;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TodoFeedAdapter extends RecyclerView.Adapter<TodoFeedAdapter.ViewHolder> {
    Context mContext;
    ViewGroup parent;
    List<TodoFeedDto> todoFeedDtoList;

    public TodoFeedAdapter(List<TodoFeedDto> todoFeedDtoList) {
        this.todoFeedDtoList = todoFeedDtoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        this.parent = parent;
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_lvfeeduser, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TodoFeedDto todoFeedDto = todoFeedDtoList.get(position);

        Uri profileUri = Uri.parse(todoFeedDto.getProfilePath());
        Picasso.with(mContext).load(profileUri).into(holder.profile);
        holder.userName.setText(todoFeedDto.getUserName());

        LinearLayout todoLayout = holder.todoLayout;
        for (TodoFeedValue value : todoFeedDto.getTodo()) {
            LinearLayout linearLayout = new LinearLayout(todoLayout.getContext());
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            linearLayout.setLayoutParams(layoutParams);

            View view = LayoutInflater.from(todoLayout.getContext()).inflate(R.layout.item_lvfeedtodo, null, false);
            CircleImageView checkImage = getCheckImage(value, view);
            TextView content = view.findViewById(R.id.content);
            content.setText(value.getContent());

            if(checkImage.getParent() != null) {
                ((ViewGroup)checkImage.getParent()).removeView(checkImage);
            }
            if(content.getParent() != null) {
                ((ViewGroup)content.getParent()).removeView(content);
            }
            linearLayout.addView(checkImage);
            linearLayout.addView(content);

            todoLayout.addView(linearLayout);
        }
    }

    @NonNull
    private CircleImageView getCheckImage(TodoFeedValue todoFeedValue, View view) {
        CircleImageView check = view.findViewById(R.id.check);
        int color = Color.parseColor(todoFeedValue.getColor());
        check.setCircleBackgroundColor(color);
        check.setBorderColor(color);
        return check;
    }

    @Override
    public int getItemCount() {
        return todoFeedDtoList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView profile;
        TextView userName;
        LinearLayout todoLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            profile = itemView.findViewById(R.id.profile);
            userName = itemView.findViewById(R.id.userName);
            todoLayout = itemView.findViewById(R.id.todoList);
        }
    }
}
