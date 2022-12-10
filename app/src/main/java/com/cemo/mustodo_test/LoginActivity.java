package com.cemo.mustodo_test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cemo.mustodo_test.api.LoginData;
import com.cemo.mustodo_test.api.LoginResponse;
import com.cemo.mustodo_test.api.RetrofitClient;
import com.cemo.mustodo_test.api.ServiceInterface;
import com.cemo.mustodo_test.api.SignUpData;
import com.cemo.mustodo_test.api.SignUpResponse;
import com.cemo.mustodo_test.api.UserData;
import com.cemo.mustodo_test.api.UserResponse;
import com.cemo.mustodo_test.data.dataControl;
import com.cemo.mustodo_test.data.dataHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText LoginEmail;
    private EditText LoginPassword;
    private Button LoginBtn;

    private ServiceInterface service;

    private dataHelper helper;
    private dataControl sqlite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(Color.parseColor("#ffffff"));

        service = RetrofitClient.getClient().create(ServiceInterface.class);

        helper = new dataHelper(
                LoginActivity.this, // context
                "Mustodouser.db", // DB 파일 이름을 적어주시면 됩니다.
                null, // Factory
                1 // 현재 생성하는 DB의 버전을 설정합니다.
        );

        LoginEmail = findViewById(R.id.login_email);
        LoginPassword = findViewById(R.id.login_pass);
        LoginBtn = findViewById(R.id.login_btn);

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUser();
            }
        });

    }

    private void checkUser(){
        String userEmail = LoginEmail.getText().toString();
        String userPassword = LoginPassword.getText().toString();

        if(userEmail.length()  == 0){
            Toast.makeText(getApplicationContext(),"이메일을 입력해주세요", Toast.LENGTH_SHORT ).show();
        } else if(userPassword.length()  == 0) {
            Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
        } else{
            startLogin( new LoginData(userEmail, userPassword)  );
        }

    }

    private void startLogin(LoginData data) {
        service.userLogin(data).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull retrofit2.Response<LoginResponse> response) {

                if(response.isSuccessful()){
                    LoginResponse result = response.body();

                    //Log.d("DATA : ", response.body().toString());

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("email", data.getEmail());
                    intent.putExtra("mode", "LOGIN_USER");
                    startActivity(intent);
                    finish();

                }else{
                    try {
                        String ERROR_MSG = response.errorBody().string();

                        if(ERROR_MSG.equals("{\"errorCode\":\"UNAUTHORIZED_ACCESS\"}")){
                            Toast.makeText(getApplicationContext(),"인증되지 않은 이메일 입니다. 인증해주세요.",Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(LoginActivity.this, AuthActivity.class);

                            intent.putExtra("email", data.getEmail());

                            startActivity(intent);
                            finish();

                        }else if(ERROR_MSG.equals("{\"errorCode\":\"LOGIN_FAILED_ERROR\"}")){
                            Toast.makeText(getApplicationContext(),"아이디, 비밀번호를 확인해주세요.",Toast.LENGTH_SHORT).show();
                            LoginEmail.setText("");
                            LoginPassword.setText("");
                        }else {
                            Toast.makeText(getApplicationContext(),"알 수 없는 에러입니다. 잠시만 기다려주세요.",Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show();
                Log.e("Register : ", t.getMessage());
                t.printStackTrace();

            }
        });
    }




    private boolean isEmailValid(String email) {
        return email.contains("@");
    }
}