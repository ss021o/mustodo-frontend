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

import com.bumptech.glide.load.engine.Resource;
import com.cemo.mustodo_test.api.RetrofitClient;
import com.cemo.mustodo_test.api.diary.DiaryDayData;
import com.cemo.mustodo_test.api.diary.DiaryServiceInterface;
import com.cemo.mustodo_test.api.diary.OpenDiaryResponse;
import com.cemo.mustodo_test.api.todo.OpenResponse;
import com.cemo.mustodo_test.api.todo.TodoDayData;
import com.cemo.mustodo_test.api.todo.TodoServiceInterface;
import com.cemo.mustodo_test.diary.OpenDiaryAdapter;
import com.cemo.mustodo_test.todo.OpenTodoAdaper;
import com.cemo.mustodo_test.todo.TodoData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Frag_feed extends Fragment {
    private View view;

    private ImageView open_profile_view;
    private Button todoBtn, projectBtn;
    Boolean activeBtn;

    private ListView todoView, diaryView;

    private TodoServiceInterface todoService;
    private DiaryServiceInterface diaryService;

    ArrayList<TodoData> todoOpenDataList = new ArrayList<TodoData>();
    OpenTodoAdaper openAdapter;

    ArrayList<DiaryDayData> diaryOpenDataList = new ArrayList<DiaryDayData>();
    OpenDiaryAdapter openDiaryAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_feed, null);

        View openView = inflater.inflate(R.layout.lv_todo_open, null, false);


        todoBtn = view.findViewById(R.id.todoBtn);
        projectBtn = view.findViewById(R.id.projectBtn);

        todoView = view.findViewById(R.id.feed_todo_layout);
        diaryView = view.findViewById(R.id.feed_diary_layout);

        todoService = RetrofitClient.getClient().create(TodoServiceInterface.class);
        diaryService = RetrofitClient.getClient().create(DiaryServiceInterface.class);

        openAdapter = new OpenTodoAdaper(view.getContext(), todoOpenDataList);
        openDiaryAdapter = new OpenDiaryAdapter(view.getContext(), diaryOpenDataList);

        open_profile_view = openView.findViewById(R.id.open_profile_view);

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

        return view;
    }

    public void SetBtnFocus(boolean check) {
        if (check) {
            todoBtn.setBackground(getResources().getDrawable(R.drawable.btn_solid_02));
            todoBtn.setTextColor(getResources().getColor(R.color.white));

            projectBtn.setBackground(getResources().getDrawable(R.drawable.btn_line_03));
            projectBtn.setTextColor(getResources().getColor(R.color.main));

            todoView.setVisibility(View.VISIBLE);
            diaryView.setVisibility(View.GONE);

            getOpenTodoList();
        } else {
            todoBtn.setBackground(getResources().getDrawable(R.drawable.btn_line_02));
            todoBtn.setTextColor(getResources().getColor(R.color.main));

            projectBtn.setBackground(getResources().getDrawable(R.drawable.btn_solid_03));
            projectBtn.setTextColor(getResources().getColor(R.color.white));

            diaryView.setVisibility(View.VISIBLE);
            todoView.setVisibility(View.GONE);
            getOpenDiaryList();
        }

    }

    public void drawOpenTodo(List<TodoDayData> item){
        if (item != null) {
            try {
                if(todoOpenDataList != null) todoOpenDataList.clear();
                for (int i = 0; i < item.size(); i++) {
                    TodoDayData dataItem = item.get(i);

                    int openId = dataItem.getId();
                    String openNick = dataItem.getNickname();
                    String userProfile = dataItem.getProfile();
                    String userMsg = dataItem.getMymsg();
                    int group_id = dataItem.getGroup_id();
                    String todo_text = dataItem.getTitle();
                    String chkDate = dataItem.getTodoDate();
                    String chkTime = dataItem.getTodoTime();
                    String todo_check = dataItem.getCheck();
                    boolean check = "1".equals(todo_check);
                    if(userProfile.equals("")){
                        userProfile = "noImg";
                    }

                    todoOpenDataList.add(new TodoData(openId, openNick, userProfile, userMsg,group_id, todo_text, chkDate, chkTime, check, 5));
                    openAdapter.notifyDataSetChanged();
                }

                todoView.setAdapter(openAdapter);
            }catch (Error e){
                e.printStackTrace();
            }

        } else {

        }
    }

    public void getOpenTodoList() {
        try {
            todoService.getOpenTodoList().enqueue(new Callback<OpenResponse>() {
                @Override
                public void onResponse(Call<OpenResponse> call, Response<OpenResponse> response) {
                    if (response.isSuccessful()) {
                        OpenResponse res = response.body();

                        if(res != null){
                            List<TodoDayData> ja = res.getMsg();
                            drawOpenTodo(ja);
                        }
                    } else {
                        try {

                        } catch (Error e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<OpenResponse> call, Throwable t) {
                    t.printStackTrace();
                }
            });

        }catch (Error e){
            e.printStackTrace();
        }
    }

    public void drawOpenDiary(List<DiaryDayData> item){
        if (item != null) {
            try {
                if(diaryOpenDataList != null) diaryOpenDataList.clear();
                for (int i = 0; i < item.size(); i++) {
                    DiaryDayData dataItem = item.get(i);

                    int openId = dataItem.getId();
                    int openUId = dataItem.getUser_id();
                    String openNick = dataItem.getNickname();
                    String userProfile = dataItem.getProfile();
                    String userMsg = dataItem.getMymsg();

                    String title = dataItem.getTitle();
                    String contents = dataItem.getContents();
                    String img = dataItem.getImg();

                    long created = dataItem.getCreated();

                    if(userProfile.equals("")){
                        userProfile = "noImg";
                    }

                    if(img.equals("")){
                        img = "noImg";
                    }

                    diaryOpenDataList.add(new DiaryDayData(openId, openUId, openNick, userProfile, userMsg, title, img, contents, created));
                    openDiaryAdapter.notifyDataSetChanged();
                }

                diaryView.setAdapter(openDiaryAdapter);
            }catch (Error e){
                e.printStackTrace();
            }

        } else {

        }
    }

    public void getOpenDiaryList(){
        try {
            diaryService.getOpenDiaryList().enqueue(new Callback<OpenDiaryResponse>() {
                @Override
                public void onResponse(Call<OpenDiaryResponse> call, Response<OpenDiaryResponse> response) {
                    if (response.isSuccessful()) {
                        OpenDiaryResponse res = response.body();

                        if(res != null){
                            List<DiaryDayData> ja = res.getMsg();

                            System.out.println(ja.size());

                            drawOpenDiary(ja);
                        }
                    } else {
                        try {

                        } catch (Error e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<OpenDiaryResponse> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }catch (Error e){
            e.printStackTrace();
        }

    }
}
