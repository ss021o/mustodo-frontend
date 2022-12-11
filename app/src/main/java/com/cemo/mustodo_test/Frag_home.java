package com.cemo.mustodo_test;


import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cemo.mustodo_test.api.RetrofitClient;
import com.cemo.mustodo_test.api.ServiceInterface;
import com.cemo.mustodo_test.api.UserData;
import com.cemo.mustodo_test.api.UserResponse;
import com.cemo.mustodo_test.api.todo.TodoDayData;
import com.cemo.mustodo_test.api.todo.TodoDayResponse;
import com.cemo.mustodo_test.api.todo.TodoMonthData;
import com.cemo.mustodo_test.api.todo.TodoMonthResponse;
import com.cemo.mustodo_test.api.todo.TodoServiceInterface;
import com.cemo.mustodo_test.data.dataControl;
import com.cemo.mustodo_test.data.dataHelper;
import com.cemo.mustodo_test.diary.DiaryData;
import com.cemo.mustodo_test.todo.TodoData;
import com.cemo.mustodo_test.todo.recyclerlist.TodoRecyclerAdapter;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.text.ParseException;
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
    private TextView dateText, textUserNick;
    private ImageButton prevBtn, nextBtn;

    private ImageView profile_view;

    private Button todoBtn, projectBtn;
    Boolean activeBtn;

    //    private ListView todoView, diaryView, lvtodoList;
    private ListView todoView, diaryView;
    private RecyclerView lvtodoList;
    TodoRecyclerAdapter adapter;

    ArrayList<TodoData> todoDataList = new ArrayList<TodoData>();
//    TodoAdapter myAdapter;

    ArrayList<DiaryData> diaryDataList;

    private FloatingActionButton WriteActionBtn, setTodoBtn, setDiaryBtn;
    private boolean fabMain_status = false;

    private ServiceInterface service;
    private TodoServiceInterface todoService;


    private dataHelper helper;
    private dataControl sqlite;

    static int userId;
    static String userNick, userEmail, userMsg, userProfile, mode;


    @SuppressLint("ResourceAsColor")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_home, null);
        profile_view = view.findViewById(R.id.profile_view);

        service = RetrofitClient.getClient().create(ServiceInterface.class);

        todoService = RetrofitClient.getClient().create(TodoServiceInterface.class);

        Bundle extra = getArguments();


        TextView txtUserNick = view.findViewById(R.id.textUserNick);
        TextView txtUserMsg = view.findViewById(R.id.profile_msg);

        if (extra != null) {
            mode = extra.getString("mode");

            if (mode.equals("GUEST")) {
                txtUserNick.setText("게스트");
                txtUserMsg.setText("로그인을 하면 더 많은 기능을 이용할 수 있어요!");
                profile_view.setImageResource(R.drawable.noimg);
            } else if (mode.equals("LOGIN_USER")) {
                userEmail = extra.getString("userEmail");
                checkUserInfo(new UserData(userEmail));
            }
        }

        profile_view.setClipToOutline(true);

        todoBtn = view.findViewById(R.id.todoBtn);
        projectBtn = view.findViewById(R.id.projectBtn);

        lvtodoList = view.findViewById(R.id.lvtodoItem);

//        todoView = view.findViewById(R.id.lvtodoItem);
        diaryView = view.findViewById(R.id.lvdiaryItem);

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

        LinearLayout todoLayout = view.findViewById(R.id.todo_layout);
        LinearLayout diaryLayout = view.findViewById(R.id.diary_layout);

        todoLayout.setVisibility(View.GONE);
        diaryLayout.setVisibility(View.GONE);

        final CompactCalendarView monthView = (CompactCalendarView) view.findViewById(R.id.month_view);

        prevBtn = (ImageButton) view.findViewById(R.id.prev_btn);
        nextBtn = (ImageButton) view.findViewById(R.id.next_btn);

        dateText = (TextView) view.findViewById(R.id.dateText);

        dateText.setText(dateFormatForMonth.format(monthView.getFirstDayOfCurrentMonth()));
        monthView.setFirstDayOfWeek(Calendar.MONDAY);


        monthView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                List<Event> events = monthView.getEvents(dateClicked);

                if (activeBtn) {
                    try {
                        if (mode.equals("GUEST")) {
                            Toast.makeText(getContext(), "로그인을 하면 더 많은 기능을 사용할 수 있어요!", Toast.LENGTH_SHORT).show();
                        } else if (mode.equals("LOGIN_USER")) {
                            getTodoListsDate(userNick, String.valueOf(dateClicked));
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getContext(), "해당 날짜 일기 보기: " + dateClicked, Toast.LENGTH_SHORT).show();
                }

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


        WriteActionBtn = (FloatingActionButton) view.findViewById(R.id.fabClickBtn);
        setTodoBtn = (FloatingActionButton) view.findViewById(R.id.fab_setTodo);
        setDiaryBtn = (FloatingActionButton) view.findViewById(R.id.fab_setDiary);


        WriteActionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFab();
            }
        });

        setTodoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mode.equals("GUEST")) {
                    Toast.makeText(getContext(), "로그인을 하면 더 많은 기능을 사용할 수 있어요!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), StartActivity.class);
                    startActivity(intent);
                } else if (mode.equals("LOGIN_USER")) {
                    Intent intent = new Intent(getActivity(), TodoActivity.class);
                    intent.putExtra("userNick", userNick);
                    intent.putExtra("userEmail", userEmail);
                    intent.putExtra("mode", "LOGIN_USER");

                    startActivity(intent);
                }
            }
        });


        setDiaryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mode.equals("GUEST")) {
                    Toast.makeText(getContext(), "로그인을 하면 더 많은 기능을 사용할 수 있어요!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), StartActivity.class);
                    startActivity(intent);
                } else if (mode.equals("LOGIN_USER")) {
                    Intent intent = new Intent(getActivity(), DiaryActivity.class);
                    intent.putExtra("userNick", userNick);
                    intent.putExtra("userEmail", userEmail);
                    intent.putExtra("mode", "LOGIN_USER");

                    startActivity(intent);
                }
            }
        });


        return view;
    }

    public void InitializeTodoData(List<TodoDayData> dataItems) {
        try {
            List<TodoData> todoDataList = new ArrayList<>();
            for (int i = 0; i < dataItems.size(); i++) {
                TodoDayData dataItem = dataItems.get(i);

                try {
                    int id = dataItem.getId();
                    String todo_text = dataItem.getTitle();
                    String todo_check = dataItem.getCheck();
                    System.out.println("todo_check = " + todo_check);
                    boolean check = "1".equals(todo_check);
                    String chkDate = formatDateTime(dataItem.getTodoDate());
                    String chkTime = dataItem.getTodoTime();
                    String groupName = dataItem.getGroupName();
                    String groupColor = dataItem.getGroupColor();

                    todoDataList.add(new TodoData(id, check, todo_text, chkDate, chkTime, groupName, groupColor));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            TodoRecyclerAdapter adapter = new TodoRecyclerAdapter(todoDataList);
            lvtodoList.setAdapter(adapter);
        } catch (Error e) {
            e.printStackTrace();
        }
    }

    public void InitializeDiaryData() {
        diaryDataList.add(new DiaryData("오늘의 일기", "인간의 같이 눈에 풍부하게 관현악이며, 주는 착목한는 그들은 우리의 따뜻한 있다.", "", "2022-12-08", 125, 150));
    }

    public void toggleFab() {
        LinearLayout todoLayout = view.findViewById(R.id.todo_layout);
        LinearLayout diaryLayout = view.findViewById(R.id.diary_layout);

        if (fabMain_status) {
            // 플로팅 액션 버튼 닫기
            // 애니메이션 추가
            ObjectAnimator fc_animation = ObjectAnimator.ofFloat(todoLayout, "translationY", 0f);
            fc_animation.start();
            ObjectAnimator fe_animation = ObjectAnimator.ofFloat(diaryLayout, "translationY", 0f);
            fe_animation.start();

            todoLayout.setVisibility(View.GONE);
            diaryLayout.setVisibility(View.GONE);
            // 메인 플로팅 이미지 변경
            WriteActionBtn.setImageResource(R.drawable.plus_icon);

        } else {
            todoLayout.setVisibility(View.VISIBLE);
            diaryLayout.setVisibility(View.VISIBLE);
            // 플로팅 액션 버튼 열기
            ObjectAnimator fc_animation = ObjectAnimator.ofFloat(todoLayout, "translationY", -40f);
            fc_animation.start();
            ObjectAnimator fe_animation = ObjectAnimator.ofFloat(diaryLayout, "translationY", -20f);
            fe_animation.start();
            // 메인 플로팅 이미지 변경
            WriteActionBtn.setImageResource(R.drawable.close_icon);
        }
        // 플로팅 버튼 상태 변경
        fabMain_status = !fabMain_status;
    }


    public void SetBtnFocus(boolean check) {

        if (check) {
            todoBtn.setBackground(getResources().getDrawable(R.drawable.btn_solid_02));
            todoBtn.setTextColor(getResources().getColor(R.color.white));

            projectBtn.setBackground(getResources().getDrawable(R.drawable.btn_line_03));
            projectBtn.setTextColor(getResources().getColor(R.color.main));

            lvtodoList.setVisibility(View.VISIBLE);
            diaryView.setVisibility(View.GONE);
        } else {
            todoBtn.setBackground(getResources().getDrawable(R.drawable.btn_line_02));
            todoBtn.setTextColor(getResources().getColor(R.color.main));

            projectBtn.setBackground(getResources().getDrawable(R.drawable.btn_solid_03));
            projectBtn.setTextColor(getResources().getColor(R.color.white));

            //diaryView.setVisibility(View.VISIBLE);
            //todoView.setVisibility(View.GONE);
        }

    }

    public void saveUserInfo(UserData data) {

        userEmail = data.getEmail();
        userId = data.getId();
        userNick = data.getNickname();
        userMsg = data.getMsg();
        userProfile = data.getProfile();

        TextView txtUserNick = view.findViewById(R.id.textUserNick);
        TextView txtUserMsg = view.findViewById(R.id.profile_msg);

        txtUserNick.setText(userNick);

        if (userMsg.equals("")) {
            txtUserMsg.setText("프로필에 목표를 설정해보세요");
        } else {
            txtUserMsg.setText(data.getMsg());
        }

        if (userProfile.equals("")) {
            profile_view.setImageResource(R.drawable.noimg);
        } else {
            String imageUrl = "https://aws-tiqets-cdn.imgix.net/images/content/f02865ee82a44cf0a87e9f72f7258fa1.jpg?auto=format&fit=crop&ixlib=python-3.2.1&q=70&s=e70b1d80f5538f189ea48bea0c48e079";
            Glide.with(this).load(imageUrl).into(profile_view);
        }

        getTodoListsToday(userNick);
        getTodoListsMonthInit(userNick);

    }

    public void getCalendarMonthInit(String selDate, int Count) {
        final CompactCalendarView monthView = (CompactCalendarView) view.findViewById(R.id.month_view);

        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);

            Date date = inputFormat.parse(selDate);
            long time = date.getTime() + (86400 * 1000);

            Event ev2 = new Event(Color.GREEN, time, "todo");

            monthView.addEvent(ev2);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getTodoListsMonthInit(String nickname) {
        Date date = new Date();
        long time = date.getTime();

        long timenow = (long) Math.floor(time / 1000L);

        todoService.getTodoMonth(nickname, String.valueOf(timenow)).enqueue(new Callback<TodoMonthResponse>() {
            @Override
            public void onResponse(Call<TodoMonthResponse> call, Response<TodoMonthResponse> response) {
                if (response.isSuccessful()) {
                    TodoMonthResponse res = response.body();

                    if (res.getCode() == 200) {
                        List<TodoMonthData> dataList = new ArrayList<>();

                        List<TodoMonthData> ja = res.getData();
                        if (ja == null) {
                            ja = new ArrayList<>();
                        }
                        for (int i = 0; i < ja.size(); i++) {
                            TodoMonthData dataItem;
                            dataItem = ja.get(i);

                            getCalendarMonthInit(dataItem.getTodoDate(), dataItem.getCount());
                        }

                    } else {

                    }

                } else {
                    try {

                    } catch (Error e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<TodoMonthResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }


    public void getTodoListsDate(String nickname, String selDate) throws ParseException {

        Date date = new Date(selDate);
        long time = (long) date.getTime();
        long timenow = (long) Math.floor(time / 1000L);


        todoService.getTodoDay(nickname, String.valueOf(timenow)).enqueue(new Callback<TodoDayResponse>() {
            @Override
            public void onResponse(Call<TodoDayResponse> call, Response<TodoDayResponse> response) {
                if (response.isSuccessful()) {
                    TodoDayResponse res = response.body();

                    if (res.getCode() == 200) {
                        List<TodoDayData> ja = res.getData();

                        if (ja == null) {
                            ja = new ArrayList<>();
                        }
                        InitializeTodoData(ja);
                    }

                } else {
                    try {

                    } catch (Error e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<TodoDayResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private String formatDateTime(String selDate) throws ParseException {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = inputFormat.parse(selDate);
        String formattedDate = outputFormat.format(date);

        return formattedDate;
    }


    private void getTodoListsToday(String nickname) {
        todoService.getTodo(nickname).enqueue(new Callback<TodoDayResponse>() {
            @Override
            public void onResponse(Call<TodoDayResponse> call, Response<TodoDayResponse> response) {
                if (response.isSuccessful()) {
                    TodoDayResponse res = response.body();

                    if (res.getCode() == 200) {
                        List<TodoDayData> ja = res.getData();

                        if (ja != null) {
                            InitializeTodoData(ja);
                        }

                    } else {

                    }

                } else {
                    try {

                    } catch (Error e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<TodoDayResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void checkUserInfo(UserData data) {

        service.userCheck(data).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    UserResponse user_response = response.body();

                    if (user_response != null) {
                        userId = user_response.getResult().getId();
                        userEmail = user_response.getResult().getEmail();
                        userNick = user_response.getResult().getNickname();
                        userMsg = user_response.getResult().getMsg();
                        userProfile = user_response.getResult().getProfile();

                        saveUserInfo(new UserData(userId, userEmail, userNick, userMsg, userProfile));
                    }

                } else {
                    try {

                    } catch (Error e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


}