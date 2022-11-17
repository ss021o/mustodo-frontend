package com.cemo.mustodo_test;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cemo.mustodo_test.api.RetrofitClient;
import com.cemo.mustodo_test.api.ServiceInterface;
import com.cemo.mustodo_test.api.todo.CategoryTodoResponse;
import com.cemo.mustodo_test.api.todo.TodoData;
import com.cemo.mustodo_test.api.todo.TodoServiceInterface;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Frag_home extends Fragment {
    private View view;
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("yyyy년 MM월", Locale.KOREA);
    private TabLayout tabNavi;
    private TextView dateText;
    private ImageButton prevBtn;
    private ImageButton nextBtn;
    private TextView textUserNick;

    String userNick;

    List<CategoryTodoResponse> categoryTodoList;

    private Button todoBtn, projectBtn;

    private FloatingActionButton fab_main, fab_sub1, fab_sub2;

    Boolean activeBtn, isFloating;

    private ExpandableListView todoView;
    private ListView projectView;

    private TodoServiceInterface service;

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

        todoView = view.findViewById(R.id.lvTodoList);
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

        isFloating = false;
        SetBtnFocus(activeBtn);

        service = RetrofitClient.getClient().create(TodoServiceInterface.class);


        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormatter.format(new Date(System.currentTimeMillis()));
        service.todoByDate(formattedDate).enqueue(new Callback<List<CategoryTodoResponse>>() {
            @Override
            public void onResponse(Call<List<CategoryTodoResponse>> call, Response<List<CategoryTodoResponse>> response) {
                if (response.isSuccessful()) {
                    categoryTodoList = response.body();
                    final TodoAdapter adapter = new TodoAdapter(getContext(), categoryTodoList);
                    todoView.setAdapter(adapter);
                    todoView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                        @Override
                        public boolean onChildClick(ExpandableListView parent, View view, int groupPosition, int childPosition, long id) {
                            Toast.makeText(view.getContext(), adapter.getChild(groupPosition, childPosition).getContent(), Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    });
                    showTodo(adapter);
                } else {
                    Log.d("response", "fail");
                }
            }

            @Override
            public void onFailure(Call<List<CategoryTodoResponse>> call, Throwable t) {

            }
        });

        final CompactCalendarView monthView = (CompactCalendarView) view.findViewById(R.id.month_view);

        prevBtn = (ImageButton) view.findViewById(R.id.prev_btn);
        nextBtn = (ImageButton) view.findViewById(R.id.next_btn);

        dateText = (TextView) view.findViewById(R.id.dateText);

        dateText.setText(dateFormatForMonth.format(monthView.getFirstDayOfCurrentMonth()));
        monthView.setFirstDayOfWeek(Calendar.MONDAY);


        fab_main = view.findViewById(R.id.floatingActionButton);
        fab_sub1 = view.findViewById(R.id.fab_sub1);
        fab_sub2 = view.findViewById(R.id.fab_sub2);

        fab_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFloating){
                    fab_sub1.setVisibility(View.INVISIBLE);
                    fab_sub2.setVisibility(View.INVISIBLE);
                    isFloating = false;
                }else {
                    fab_sub1.setVisibility(View.VISIBLE);
                    fab_sub2.setVisibility(View.VISIBLE);
                    isFloating = true;
                }

            }
        });


        fab_sub1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TodoActivity.class);
                startActivity(intent);
            }
        });


        monthView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                setTodoListByDate(dateClicked);
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

    private void setTodoListByDate(Date date) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormatter.format(date);
        service.todoByDate(formattedDate).enqueue(new Callback<List<CategoryTodoResponse>>() {
            @Override
            public void onResponse(Call<List<CategoryTodoResponse>> call, Response<List<CategoryTodoResponse>> response) {
                if (response.isSuccessful()) {
                    categoryTodoList = response.body();
                    final TodoAdapter adapter = new TodoAdapter(getContext(),categoryTodoList);
                    todoView.setAdapter(adapter);
                    showTodo(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<CategoryTodoResponse>> call, Throwable t) {

            }
        });
    }

    private void showTodo(TodoAdapter adapter) {
        for (int i = 0; i < adapter.getGroupCount(); i++) {
            todoView.expandGroup(i);
        }
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
