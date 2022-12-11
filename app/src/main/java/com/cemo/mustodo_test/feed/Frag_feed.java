package com.cemo.mustodo_test.feed;

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
import androidx.recyclerview.widget.RecyclerView;

import com.cemo.mustodo_test.R;
import com.cemo.mustodo_test.api.RetrofitClient;
import com.cemo.mustodo_test.api.diary.DiaryServiceInterface;
import com.cemo.mustodo_test.api.diary.OpenDiaryResponse;
import com.cemo.mustodo_test.api.todo.OpenResponse;
import com.cemo.mustodo_test.api.todo.TodoDayData;
import com.cemo.mustodo_test.api.todo.TodoServiceInterface;
import com.cemo.mustodo_test.feed.diary.DiaryRecyclerAdapter;
import com.cemo.mustodo_test.feed.diary.OpenDiaryData;
import com.cemo.mustodo_test.feed.todo.TodoFeedRecyclerAdapter;
import com.cemo.mustodo_test.todo.TodoData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Frag_feed extends Fragment {
    private View view;

    private ImageView open_profile_view;
    private Button todoBtn, projectBtn;
    Boolean activeBtn;

    private RecyclerView diaryView;
    private RecyclerView todoView;

    private TodoServiceInterface todoService;
    private DiaryServiceInterface diaryService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_feed, null);


        todoBtn = view.findViewById(R.id.todoBtn);
        projectBtn = view.findViewById(R.id.projectBtn);

        todoView = view.findViewById(R.id.feed_todo_layout);
        diaryView = view.findViewById(R.id.feed_diary_layout);

        todoService = RetrofitClient.getClient().create(TodoServiceInterface.class);
        diaryService = RetrofitClient.getClient().create(DiaryServiceInterface.class);

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

            diaryService.getDiaryFeed().enqueue(new Callback<OpenDiaryResponse>() {
                @Override
                public void onResponse(Call<OpenDiaryResponse> call, Response<OpenDiaryResponse> response) {
                    if (response.isSuccessful()) {
                        OpenDiaryResponse res = response.body();

                        List<OpenDiaryData> ja = res.getData();
                        if (ja == null) {
                            ja = new ArrayList<>();
                        }
                        DiaryRecyclerAdapter adapter = new DiaryRecyclerAdapter(ja);
                        diaryView.setAdapter(adapter);
                    } else {
                        System.out.println("response = " + response);
                    }
                }

                @Override
                public void onFailure(Call<OpenDiaryResponse> call, Throwable t) {
                    t.printStackTrace();
                }
            });
            diaryView.setVisibility(View.VISIBLE);
            todoView.setVisibility(View.GONE);

        }

    }

    public void drawOpenTodo(List<TodoDayData> item){
        List<TodoData> todoDataList = new ArrayList<>();

        for (int i = 0; i < item.size(); i++) {
            TodoDayData dataItem = item.get(i);

            System.out.println(dataItem);

            String openNick = dataItem.getNickname();
            String userProfile = dataItem.getProfile();
            String userMsg = dataItem.getMymsg();
            String todo_text = dataItem.getTitle();
            String color = dataItem.getGroupColor();
            if(userProfile.equals("")){
                userProfile = "noImg";
            }

            System.out.println(openNick + " " + userProfile + " " + userMsg + " " + todo_text + " " + color);
            todoDataList.add(new TodoData(openNick, todo_text, userMsg, userProfile, color));
        }

        TodoFeedRecyclerAdapter adapter = new TodoFeedRecyclerAdapter(todoDataList);
        todoView.setAdapter(adapter);
    }

    public void getOpenTodoList() {
        try {
            todoService.getOpenTodoList().enqueue(new Callback<OpenResponse>() {
                @Override
                public void onResponse(Call<OpenResponse> call, Response<OpenResponse> response) {
                    if (response.isSuccessful()) {
                        OpenResponse res = response.body();

                        List<TodoDayData> ja = res.getMsg();
                        if (ja == null) {
                            ja = new ArrayList<>();
                        }
                        drawOpenTodo(ja);

                    } else {
                        System.out.println("response = " + response);
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
}
