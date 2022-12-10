package com.cemo.mustodo_test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.cemo.mustodo_test.api.RetrofitClient;
import com.cemo.mustodo_test.api.ServiceInterface;
import com.cemo.mustodo_test.api.todo.TodoDayData;
import com.cemo.mustodo_test.api.todo.TodoGroupData;
import com.cemo.mustodo_test.api.todo.TodoGroupResponse;
import com.cemo.mustodo_test.api.todo.TodoResponse;
import com.cemo.mustodo_test.api.todo.TodoServiceInterface;
import com.cemo.mustodo_test.todo.ProjectAdapter;
import com.cemo.mustodo_test.todo.TodoData;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TodoActivity extends AppCompatActivity {

    private Button sel_date_btn, sel_time_btn, sel_project_btn, sel_open_btn, sel_repeat_btn, btnGroupCreate, btnTodoCreate;
    
    private TextView sheet_title;

    private EditText todo_title;

    private ImageView backArrow;

    private ServiceInterface service;
    private TodoServiceInterface todoService;

    ArrayList<TodoGroupData> repeatDataList1 = new ArrayList<TodoGroupData>();
    ArrayList<String> repeatDataList2 = new ArrayList<String>();
    ArrayList<String> repeatDataList3 = new ArrayList<String>();

    ArrayAdapter<String> adapter, adapter2;

    static List<TodoGroupData> projects;
    static int group_id, sel_group_id = 0;


    ProjectAdapter myAdapter;

    String userNick, userEmail, mode;

    static BottomSheetDialog bottomSheetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(Color.parseColor("#ffffff"));

        userNick = getIntent().getStringExtra("userNick");
        userEmail = getIntent().getStringExtra("userEmail");
        mode = getIntent().getStringExtra("mode");

        service = RetrofitClient.getClient().create(ServiceInterface.class);
        todoService = RetrofitClient.getClient().create(TodoServiceInterface.class);

        sel_date_btn = findViewById(R.id.sel_date_btn);
        sel_time_btn = findViewById(R.id.sel_time_btn);
        sel_project_btn = findViewById(R.id.sel_project_btn);
        sel_open_btn = findViewById(R.id.sel_open_btn);
        sel_repeat_btn = findViewById(R.id.sel_repeat_btn);
        todo_title = findViewById(R.id.todo_title);
        btnTodoCreate = findViewById(R.id.btnTodoCreate);

        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        if(mMinute > 30){
            sel_time_btn.setText(mHour+1 +" : 00");
        }else{
            sel_time_btn.setText(mHour +" : 30");
        }

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("select_project"));



        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                if(year ==mYear && month == mMonth && dayOfMonth==mDay){
                    sel_date_btn.setText("오늘");
                }else{
                    sel_date_btn.setText(year + "년 " +(month+1) + "월 " + dayOfMonth +"일");
                }
            }
        }, mYear, mMonth, mDay);

        sel_date_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });

        int alarmHour=0, alarmMinute=0;
        final String[] texthour = new String[1];
        final String[] textminute = new String[1];


        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        texthour[0] = String.valueOf(hourOfDay);

                        if(minute > 9){
                            textminute[0] = String.valueOf(minute);
                        }else{
                            textminute[0] = "0"+minute;
                        }

                        sel_time_btn.setText(texthour[0] +" : " +textminute[0]);
                    }
                },alarmHour, alarmMinute, false);


        sel_time_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog.show();
            }
        });



        backArrow = findViewById(R.id.backArrow);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TodoActivity.this, MainActivity.class);

                intent.putExtra("email", userEmail);
                intent.putExtra("mode", mode);
                startActivity(intent);


                finish();
            }
        });


        getTodoGroup(userNick);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.bottom_sheet, null, false);
        bottomSheetDialog = new BottomSheetDialog(this);

        bottomSheetDialog.setContentView(view);

        sheet_title = view.findViewById(R.id.sheet_title);
        LinearLayout bottom_sheet_btn_group = view.findViewById(R.id.bottom_sheet_btn_group);
        LinearLayout bottom_sheet_btn = view.findViewById(R.id.bottom_sheet_btn);

        btnGroupCreate = view.findViewById(R.id.btnGroupCreate);

        btnGroupCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "프로젝트 생성", Toast.LENGTH_SHORT).show();
                bottomSheetDialog.dismiss();

                View view2 = inflater.inflate(R.layout.bottom_sheet2, null, false);
                bottomSheetDialog.setContentView(view2);

                bottomSheetDialog.show();

                Button btnOK2, btnClose2;

                EditText group_id = view2.findViewById(R.id.group_id);

                btnOK2 = view2.findViewById(R.id.btnOk2);
                btnOK2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setTodoGroup(userNick, group_id.getText().toString(), "#cccccc");
                        bottomSheetDialog.dismiss();
                    }
                });

                btnClose2 = view2.findViewById(R.id.btnCancel2);
                btnClose2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                    }
                });



            }
        });

        sel_project_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sheet_title.setText("프로젝트 선택");

                bottomSheetDialog.setContentView(view);

                getTodoGroup(userNick);


                repeatDataList1.clear();

                if(projects !=null){
                    for(int i = 0; i<projects.size() ;i++){

                        int id = projects.get(i).getId();
                        String protitle = projects.get(i).getTitle();
                        String procolor = projects.get(i).getColor();

                        repeatDataList1.add(new TodoGroupData(protitle,id, procolor));
                    }

                    myAdapter = new ProjectAdapter(getApplicationContext(), repeatDataList1);
                    ListView lvSheet = view.findViewById(R.id.bt_sheed_lv);
                    lvSheet.setAdapter(myAdapter);
                }

                if(!sel_project_btn.equals("프로젝트 선택")){
                    bottomSheetDialog.dismiss();
                }


                bottom_sheet_btn.setVisibility(View.VISIBLE);
                bottom_sheet_btn_group.setVisibility(View.GONE);

                bottomSheetDialog.show();
            }
        });


        sel_open_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sheet_title.setText("공개 설정");

                bottomSheetDialog.setContentView(view);

                repeatDataList2.clear();

                repeatDataList2.add("나만 보기");
                repeatDataList2.add("전체 공개");

                final String[] selecteText1 = new String[1];

                adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_single_choice,repeatDataList2);

                ListView lvSheet = view.findViewById(R.id.bt_sheed_lv);
                lvSheet.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                lvSheet.setAdapter(adapter);

                lvSheet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    // 콜백매개변수는 순서대로 어댑터뷰, 해당 아이템의 뷰, 클릭한 순번, 항목의 아이디
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        selecteText1[0] = repeatDataList2.get(i).toString();
                    }
                });

                Button btnOK, btnClose;

                bottom_sheet_btn.setVisibility(View.GONE);
                bottom_sheet_btn_group.setVisibility(View.VISIBLE);

                btnOK = view.findViewById(R.id.btnOk);
                btnOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sel_open_btn.setText(selecteText1[0]);

                        bottomSheetDialog.dismiss();
                    }
                });

                btnClose = view.findViewById(R.id.btnCancel);
                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                    }
                });


                bottomSheetDialog.show();
            }
        });

        sel_repeat_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sheet_title.setText("반복 설정");

                bottomSheetDialog.setContentView(view);

                repeatDataList3.clear();

                repeatDataList3.add("반복 안 함");
                repeatDataList3.add("매일");
                repeatDataList3.add("매주");
                repeatDataList3.add("매월");
                repeatDataList3.add("매년");

                final String[] selecteText = new String[1];


                adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_single_choice,repeatDataList3);

                ListView lvSheet = view.findViewById(R.id.bt_sheed_lv);
                lvSheet.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                lvSheet.setAdapter(adapter);

                lvSheet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    // 콜백매개변수는 순서대로 어댑터뷰, 해당 아이템의 뷰, 클릭한 순번, 항목의 아이디
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        selecteText[0] = repeatDataList3.get(i).toString();
                    }
                });

                Button btnOK, btnClose;

                bottom_sheet_btn.setVisibility(View.GONE);
                bottom_sheet_btn_group.setVisibility(View.VISIBLE);

                btnOK = view.findViewById(R.id.btnOk);
                btnOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sel_repeat_btn.setText(selecteText[0]);

                        bottomSheetDialog.dismiss();
                    }
                });

                btnClose = view.findViewById(R.id.btnCancel);
                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                    }
                });

                bottomSheetDialog.show();
            }
        });

        btnTodoCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = todo_title.getText().toString();

                java.util.Date Now = new Date();
                String formatToday, formatDay, beforeDay;


                SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
                formatToday = outputFormat.format(Now);

                if(title.length() == 0){
                    Toast.makeText(getApplicationContext(), "할 일을 작성해주세요!", Toast.LENGTH_SHORT).show();
                }else if(sel_project_btn.getText().equals("프로젝트 선택")){
                    Toast.makeText(getApplicationContext(), "프로젝트를 선택해주세요!", Toast.LENGTH_SHORT).show();
                }else{
                    if(!sel_date_btn.getText().toString().equals("오늘")){
                        beforeDay = sel_date_btn.getText().toString();
                        formatDay = beforeDay.replace("년 ", "-");
                        formatDay = formatDay.replace("월 ", "-");
                        formatDay = formatDay.replace("일", "");
                    }else{
                        formatDay = "";
                    }
                    String setDate = sel_date_btn.getText().toString().equals("오늘") ? formatToday : formatDay;
                    String setTime = sel_time_btn.getText().toString().replace(" ", "");
                    boolean isOpen = sel_open_btn.getText().toString().equals("전체 공개") ? true : false;
                    int isLevel = 5;

                    TodoDayData data = new TodoDayData(group_id, title, setDate, setTime, isOpen, isLevel);

                    setTodoItem(data);
                }

            }
        });



    }

    public void setTodoItem(TodoDayData data){
        todoService.setTodo(userNick, data).enqueue(new Callback<TodoResponse>() {
            @Override
            public void onResponse(Call<TodoResponse> call, Response<TodoResponse> response) {
                if(response.isSuccessful()) {
                    TodoResponse res = response.body();
                    if(res.getCode() == 200){
                        res.getCode();
                        Toast.makeText(getApplicationContext(), "생성 성공", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(TodoActivity.this, MainActivity.class);

                        intent.putExtra("email", userEmail);
                        intent.putExtra("mode", mode);
                        startActivity(intent);

                        finish();
                    }
                }else{
                    try {

                    }catch (Error e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<TodoResponse> call, Throwable t) {

            }
        });
    }

    public void setTodoGroup(String userNick, String title, String color){
        todoService.setTodoGroup(userNick, new TodoGroupData(title, color)).enqueue(new Callback<TodoGroupResponse>() {
            @Override
            public void onResponse(Call<TodoGroupResponse> call, Response<TodoGroupResponse> response) {
                if(response.isSuccessful()) {
                    TodoGroupResponse res = response.body();
                    if(res.getCode() == 200){
                        res.getId();
                        Toast.makeText(getApplicationContext(), "생성 성공", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    try {

                    }catch (Error e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<TodoGroupResponse> call, Throwable t) {

            }
        });
    }


    public void getTodoGroup(String userNick){
        todoService.getTodoGroup(userNick).enqueue(new Callback<TodoGroupResponse>() {
            @Override
            public void onResponse(Call<TodoGroupResponse> call, Response<TodoGroupResponse> response) {
                if(response.isSuccessful()) {
                    TodoGroupResponse res = response.body();

                    if(res.getCode() == 200){
                        List<TodoGroupData> ja = res.getData();
                        if(ja != null) projects = ja;
                    }

                }else{
                    try {

                    }catch (Error e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<TodoGroupResponse> call, Throwable t) {

            }
        });

    }
    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String Group_title = intent.getStringExtra("title");
            group_id = intent.getIntExtra("group_id", sel_group_id);
            String color = intent.getStringExtra("color");
            sel_project_btn.setText(Group_title);

            bottomSheetDialog.dismiss();
        }
    };

}