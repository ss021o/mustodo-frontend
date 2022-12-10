package com.cemo.mustodo_test.diary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cemo.mustodo_test.R;
import com.cemo.mustodo_test.api.diary.DiaryDayData;
import com.cemo.mustodo_test.todo.TodoData;

import java.util.ArrayList;

public class OpenDiaryAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater mLayoutInflater;
    ArrayList<DiaryDayData> items = new ArrayList<DiaryDayData>();

    public OpenDiaryAdapter(Context context, ArrayList<DiaryDayData> data) {
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
    public DiaryDayData getItem(int position) {
        return items.get(position);
    }

    public void addItem(DiaryDayData item){
        items.add(item);
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.lv_diary_open, null);

        DiaryDayData listViewItem = items.get(position);

        TextView userNick = (TextView) view.findViewById(R.id.open_diary_textUserNick);
        TextView userMsg = (TextView) view.findViewById(R.id.open_diary_profile_text);
        ImageView userProfile = (ImageView) view.findViewById(R.id.open_diary_profile_view);

        ImageView img = (ImageView) view.findViewById(R.id.open_diary_img);
        TextView title = (TextView) view.findViewById(R.id.open_diary_title);
        TextView contents = (TextView) view.findViewById(R.id.open_diary_contents);


        userNick.setText(items.get(position).getNickname());

        if(items.get(position).getProfile().equals("noImg")){
            userProfile.setImageResource(R.drawable.noimg);
        }else{
            String imageUrl = "https://aws-tiqets-cdn.imgix.net/images/content/f02865ee82a44cf0a87e9f72f7258fa1.jpg?auto=format&fit=crop&ixlib=python-3.2.1&q=70&s=e70b1d80f5538f189ea48bea0c48e079";
            Glide.with(mContext).load(imageUrl).into(userProfile);
        }

        if(items.get(position).getImg().equals("noImg")){
            img.setVisibility(View.GONE);
        }else{
            String imageUrl = items.get(position).getImg();
            Glide.with(mContext).load(imageUrl).into(img);
        }

        userProfile.setClipToOutline(true);

        title.setText(items.get(position).getTitle());
        contents.setText(items.get(position).getContents());

        return view;
    }
}