package com.cemo.mustodo_test.feed.diary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cemo.mustodo_test.R;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DiaryRecyclerAdapter extends RecyclerView.Adapter<DiaryRecyclerAdapter.ViewHolder> {
    Context context;
    List<OpenDiaryData> items;

    @SuppressLint("NewApi")
    public DiaryRecyclerAdapter(List<OpenDiaryData> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.lv_diary_open, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        OpenDiaryData data = items.get(i);
        holder.title.setText(data.getTitle());
        holder.userMsg.setText(data.getUserMsg());
        holder.contents.setText(data.getContents());
        holder.userName.setText(data.getUserName());
        holder.profile.setImageResource(R.drawable.noimg);
    }
    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView userName;
        ImageView profile;
        TextView userMsg;
        TextView title;
        TextView contents;
        ImageView image;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.userName);
            userMsg = itemView.findViewById(R.id.userMsg);
            profile = itemView.findViewById(R.id.profileImage);
            title = itemView.findViewById(R.id.diary_title);
            contents = itemView.findViewById(R.id.diary_contents);
        }
    }
}