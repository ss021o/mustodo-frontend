package com.cemo.mustodo_test;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Frag_feed extends Fragment {
    private View view;

    private ImageView profile_view;
    private Button todoBtn, projectBtn;
    Boolean activeBtn;

    private ListView todoView, diaryView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_feed, null);

        //profile_view = view.findViewById(R.id.profile_view);
        //profile_view.setClipToOutline(true);

        todoBtn = view.findViewById(R.id.todoBtn);
        projectBtn = view.findViewById(R.id.projectBtn);

        todoView = view.findViewById(R.id.feed_todo_layout);
        diaryView = view.findViewById(R.id.feed_diary_layout);

        todoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeBtn = true;
                SetBtnFocus(activeBtn);

            }
        });

        projectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeBtn = false;
                SetBtnFocus(activeBtn);
            }
        });

        activeBtn = true;
        SetBtnFocus(activeBtn);

        return  view;
    }

    public void SetBtnFocus(boolean check)
    {

        if(check){


            todoBtn.setBackground(getResources().getDrawable(R.drawable.btn_solid_02));
            todoBtn.setTextColor(getResources().getColor(R.color.white));

            projectBtn.setBackground(getResources().getDrawable(R.drawable.btn_line_03));
            projectBtn.setTextColor(getResources().getColor(R.color.main));

            todoView.setVisibility(View.VISIBLE);
            diaryView.setVisibility(View.GONE);
        }else{
            todoBtn.setBackground(getResources().getDrawable(R.drawable.btn_line_02));
            todoBtn.setTextColor(getResources().getColor(R.color.main));

            projectBtn.setBackground(getResources().getDrawable(R.drawable.btn_solid_03));
            projectBtn.setTextColor(getResources().getColor(R.color.white));

            diaryView.setVisibility(View.VISIBLE);
            todoView.setVisibility(View.GONE);
        }

    }
}
