package com.cemo.mustodo_test;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.cemo.mustodo_test.api.GoalData;
import com.cemo.mustodo_test.api.RetrofitClient;
import com.cemo.mustodo_test.api.ServiceInterface;
import com.cemo.mustodo_test.api.UserData;
import com.cemo.mustodo_test.api.UserResponse;
import com.cemo.mustodo_test.api.todo.StatsResponse;
import com.cemo.mustodo_test.api.todo.TodoServiceInterface;
import com.cemo.mustodo_test.stats.StatData;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Frag_my extends Fragment {
    private View view;

    static int userId;
    static String userNick, userEmail, userMsg, userProfile, mode;

    TextView my_user_nick, my_msg_text, my_notchecked_text, my_checked_text, my_notfinish_text;
    ImageView my_profile_view;

    Button go_stats_btn, go_goal_btn;

    private ServiceInterface service;
    private TodoServiceInterface todoService;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_my, null);

        Bundle extra = getArguments();

        service = RetrofitClient.getClient().create(ServiceInterface.class);
        todoService = RetrofitClient.getClient().create(TodoServiceInterface.class);

        my_user_nick = view.findViewById(R.id.my_user_nick);
        my_msg_text = view.findViewById(R.id.my_msg_text);
        my_profile_view = view.findViewById(R.id.my_profile_view);
        my_profile_view.setClipToOutline(true);

        my_notchecked_text = view.findViewById(R.id.my_notchecked_text);
        my_checked_text = view.findViewById(R.id.my_checked_text);
        my_notfinish_text = view.findViewById(R.id.my_notfinish_text);

        if(extra != null){
            mode = extra.getString("mode");

            if(mode.equals("GUEST")){
                my_user_nick.setText("?????????");
                my_msg_text.setText("???????????? ?????? ??? ?????? ????????? ????????? ??? ?????????!");
                my_profile_view.setImageResource(R.drawable.noimg);
            }else if(mode.equals("LOGIN_USER")){
                userEmail = extra.getString("userEmail");
                checkUserInfo(new UserData(userEmail));
            }
        }

        go_stats_btn = view.findViewById(R.id.go_stats_btn);

        go_stats_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode.equals("GUEST")){
                    Toast.makeText(getContext(),"???????????? ?????? ??? ?????? ????????? ????????? ??? ?????????!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), StartActivity.class);
                    startActivity(intent);
                }else if(mode.equals("LOGIN_USER")){
                    Intent intent = new Intent(getActivity(), StatActivity.class);
                    intent.putExtra("userNick", userNick);
                    intent.putExtra("userEmail", userEmail);
                    intent.putExtra("mode", "LOGIN_USER");

                    startActivity(intent);
                }
            }
        });


        go_goal_btn = view.findViewById(R.id.go_goal_btn);

        go_goal_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ad = new AlertDialog.Builder(getContext());
                ad.setTitle(userNick  + "?????? ????????? ??????????????????.");

                // EditText ????????????
                final EditText et = new EditText(getContext());

                FrameLayout container = new FrameLayout(getContext());
                FrameLayout.LayoutParams params = new  FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.leftMargin = 50;
                params.rightMargin = 50;
                et.setLayoutParams(params);
                container.addView(et);
                ad.setView(container);

                // ?????? ?????? ??????
                ad.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String value = et.getText().toString();
                        if (value.trim().equals("")) {
                            Toast.makeText(getContext(),"????????? ??????????????????!", Toast.LENGTH_SHORT).show();
                            return;
                        }else {
                            setUserMsg(userNick, new GoalData(value));
                        }

                        dialog.dismiss();
                    }
                });

                // ?????? ?????? ??????
                ad.setNegativeButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                ad.show();

            }
        });

        return  view;
    }

    public void saveUserInfo(UserData data){

        userEmail = data.getEmail();
        userId = data.getId();
        userNick = data.getNickname();
        userMsg = data.getMsg();
        userProfile = data.getProfile();


        my_user_nick.setText(userNick);

        if(userMsg.equals("")){
            my_msg_text.setText("???????????? ????????? ??????????????????");
        }else {
            my_msg_text.setText(data.getMsg());
        }

        if(userProfile.equals("")){
            my_profile_view.setImageResource(R.drawable.noimg);
        }else{
            String imageUrl = "https://aws-tiqets-cdn.imgix.net/images/content/f02865ee82a44cf0a87e9f72f7258fa1.jpg?auto=format&fit=crop&ixlib=python-3.2.1&q=70&s=e70b1d80f5538f189ea48bea0c48e079";
            Glide.with(this).load(imageUrl).into(my_profile_view);
        }


    }

    private void checkUserInfo(UserData data){

        service.userCheck(data).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if(response.isSuccessful()) {
                    UserResponse user_response = response.body();

                    if(user_response != null){
                        userId = user_response.getResult().getId();
                        userEmail = user_response.getResult().getEmail();
                        userNick = user_response.getResult().getNickname();
                        userMsg = user_response.getResult().getMsg();
                        userProfile = user_response.getResult().getProfile();

                        saveUserInfo(new UserData(userId, userEmail, userNick, userMsg, userProfile));

                        getUSerTodayStats(userNick);
                    }

                }else{
                    try {

                    }catch (Error e){
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

    public void getUSerTodayStats(String userNick){
        try {
            todoService.getTodoTodayStats(userNick).enqueue(new Callback<StatsResponse>() {
                @Override
                public void onResponse(Call<StatsResponse> call, Response<StatsResponse> response) {
                    if(response.isSuccessful()) {
                        StatsResponse statsResponse = response.body();
                        if(statsResponse != null){

                            StatData data = statsResponse.getData();

                            my_notchecked_text.setText( String.valueOf( data.getNotchecked() ) );
                            my_checked_text.setText( String.valueOf( data.getChecked() ) );
                            my_notfinish_text.setText( String.valueOf( data.getNotfinish() ) );

                        }
                    }
                }

                @Override
                public void onFailure(Call<StatsResponse> call, Throwable t) {

                }
            });
        }catch (Error e){
            e.printStackTrace();
        }
    }

    public void setUserMsg(String userNick, GoalData mymsg){
        service.setUserGoal(userNick, mymsg).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()) {
                    ResponseBody user_response = response.body();
                    if(user_response != null){
                        System.out.println(mymsg);

                        Toast.makeText(getContext(), "SUCCESS", Toast.LENGTH_SHORT).show();
                        checkUserInfo(new UserData(userEmail));
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
