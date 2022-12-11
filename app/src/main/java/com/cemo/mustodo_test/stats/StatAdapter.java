package com.cemo.mustodo_test.stats;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.cemo.mustodo_test.R;
import com.cemo.mustodo_test.popular.MsgData;

import java.util.ArrayList;

public class StatAdapter extends BaseAdapter {
    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<StatMonthData> sample;

    public StatAdapter(Context context, ArrayList<StatMonthData> data) {
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
    public StatMonthData getItem(int position) {
        return sample.get(position);
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.gv_callendar_cell, null);

        final GradientDrawable drawable = (GradientDrawable) ContextCompat.getDrawable(mContext, R.drawable.btn_gray);


        TextView gv_stats_cell = view.findViewById(R.id.gv_stats_cell);
        TextView gv_days_cell = view.findViewById(R.id.gv_days_cell);

        double per = ((double)sample.get(position).getDaysChecked() / (double)sample.get(position).getDaysTotal()) * 100;

        int stat = (int) per;

        if(stat == 0){
            drawable.setColor(Color.parseColor("#EBEBEB"));
            gv_stats_cell.setTextColor(Color.parseColor("#808080"));
        }else if(stat>0 && stat <=20){
            drawable.setColor(Color.parseColor("#B0D6FF"));
            gv_stats_cell.setTextColor(Color.WHITE);
        }else if(stat>20 && stat <=40){
            drawable.setColor(Color.parseColor("#5DABFF"));
            gv_stats_cell.setTextColor(Color.WHITE);
        }else if(stat>40 && stat <=60){
            drawable.setColor(Color.parseColor("#007BFF"));
            gv_stats_cell.setTextColor(Color.WHITE);
        }else if(stat>60 && stat <=80){
            drawable.setColor(Color.parseColor("#003A79"));
            gv_stats_cell.setTextColor(Color.WHITE);
        }else{
            drawable.setColor(Color.parseColor("#001022"));
            gv_stats_cell.setTextColor(Color.WHITE);
        }

        gv_stats_cell.setBackground(drawable);

        gv_stats_cell.setText(String.valueOf(sample.get(position).getDaysTotal()));
        gv_days_cell.setText(sample.get(position).getDays());


        return view;
    }
}