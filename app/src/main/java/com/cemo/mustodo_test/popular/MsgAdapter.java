package com.cemo.mustodo_test.popular;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cemo.mustodo_test.R;
import com.cemo.mustodo_test.diary.DiaryData;

import java.util.ArrayList;

public class MsgAdapter extends BaseAdapter {
    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<MsgData> sample;

    public MsgAdapter(Context context, ArrayList<MsgData> data) {
        mContext = context;
        sample = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return sample.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public MsgData getItem(int position) {
        return sample.get(position);
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.lv_msg_view, null);

        TextView msg_contents = view.findViewById(R.id.msg_contents);
        TextView msg_talker = view.findViewById(R.id.msg_talker);
        TextView msg_like = view.findViewById(R.id.msg_like);

        ImageView msg_like_btn = view.findViewById(R.id.msg_like_btn);

        msg_contents.setText(sample.get(position).getContents());
        msg_talker.setText("- "+sample.get(position).getTalker());
        msg_like.setText(String.valueOf(sample.get(position).getLike()));

        msg_like_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (msg_like_btn != null) {

                    if(msg_like_btn.isSelected()){
                        msg_like_btn.setSelected(false);
                        msg_like.setText(String.valueOf(sample.get(position).getLike() - 1));
                    }else {
                        msg_like_btn.setSelected(true);
                        msg_like.setText(String.valueOf(sample.get(position).getLike() + 1));
                    }
                }


                //Toast.makeText(mContext,sample.get(position).toString() , Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }
}