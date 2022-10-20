package com.cemo.mustodo_test;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Frag_home extends Fragment {
    private View view;
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("yyyy년 MM월", Locale.KOREA);
    private TabLayout tabNavi;
    private TextView dateText;
    private ImageButton prevBtn;
    private ImageButton nextBtn;
    private TextView textUserNick;

    String userNick;

    ArrayList<TodoData> todoDataList;

    private Button todoBtn, projectBtn;
    Boolean activeBtn;

    private ListView todoView;
    private ListView projectView;


    @SuppressLint("ResourceAsColor")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_home, null);


        Bundle extra = getArguments();
        if(extra != null){
            userNick = extra.getString("userNick");
        }
        textUserNick = view.findViewById(R.id.textUserNick);
        textUserNick.setText(userNick);

        todoBtn = view.findViewById(R.id.todoBtn);
        projectBtn = view.findViewById(R.id.projectBtn);

        todoView = view.findViewById(R.id.lvtodoItem);
        projectView = view.findViewById(R.id.lvprojectItem);

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


        this.InitializeTodoData();

        ListView listView = (ListView)view.findViewById(R.id.lvtodoItem);

        final TodoAdapter myAdapter = new TodoAdapter(getContext(),todoDataList);

        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id){
                Toast.makeText(view.getContext(),
                        myAdapter.getItem(position).getTodoText(),
                        Toast.LENGTH_LONG).show();
            }
        });


        final CompactCalendarView monthView = (CompactCalendarView) view.findViewById(R.id.month_view);

        prevBtn = (ImageButton) view.findViewById(R.id.prev_btn);
        nextBtn = (ImageButton) view.findViewById(R.id.next_btn);

        dateText = (TextView) view.findViewById(R.id.dateText);

        dateText.setText(dateFormatForMonth.format(monthView.getFirstDayOfCurrentMonth()));
        monthView.setFirstDayOfWeek(Calendar.MONDAY);


        monthView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                dateText.setText(dateFormatForMonth.format(monthView.getFirstDayOfCurrentMonth()));
            }
        });

        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monthView.scrollLeft();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monthView.scrollRight();
            }
        });

        return  view;
    }

    public void InitializeTodoData()
    {
        todoDataList = new ArrayList<TodoData>();

        todoDataList.add(new TodoData(false, "캡스톤01","4days"));
        todoDataList.add(new TodoData(false, "캡스톤02","2days"));
        todoDataList.add(new TodoData(false, "캡스톤03","1days"));
        todoDataList.add(new TodoData(false, "캡스톤04","4days"));
        todoDataList.add(new TodoData(false, "캡스톤05","2days"));
        todoDataList.add(new TodoData(false, "캡스톤06","1days"));
        todoDataList.add(new TodoData(false, "캡스톤07","4days"));
        todoDataList.add(new TodoData(false, "캡스톤08","2days"));
        todoDataList.add(new TodoData(false, "캡스톤09","1days"));
    }

    public void SetBtnFocus(boolean check)
    {
        if(check){
            todoBtn.setBackground(getResources().getDrawable(R.drawable.btn_solid_02));
            todoBtn.setTextColor(getResources().getColor(R.color.white));

            projectBtn.setBackground(getResources().getDrawable(R.drawable.btn_line_03));
            projectBtn.setTextColor(getResources().getColor(R.color.main));

            todoView.setVisibility(View.VISIBLE);
            projectView.setVisibility(View.GONE);
        }else{
            todoBtn.setBackground(getResources().getDrawable(R.drawable.btn_line_02));
            todoBtn.setTextColor(getResources().getColor(R.color.main));

            projectBtn.setBackground(getResources().getDrawable(R.drawable.btn_solid_03));
            projectBtn.setTextColor(getResources().getColor(R.color.white));

            projectView.setVisibility(View.VISIBLE);
            todoView.setVisibility(View.GONE);
        }

    }


}
