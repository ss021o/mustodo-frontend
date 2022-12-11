package com.cemo.mustodo_test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.cemo.mustodo_test.api.AuthData;
import com.cemo.mustodo_test.api.AuthResponse;
import com.cemo.mustodo_test.api.LoginData;
import com.cemo.mustodo_test.api.LoginResponse;
import com.cemo.mustodo_test.api.RetrofitClient;
import com.cemo.mustodo_test.api.ServiceInterface;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;

public class AuthActivity extends AppCompatActivity {

    private RelativeLayout preLayout, authLayout;
    private Button btnPreAuth, btnAuth;
    private EditText editAuthCode;

    private  String intentEmail;

    private ServiceInterface service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(Color.parseColor("#ffffff"));

        Intent intent = getIntent();
        intentEmail = intent.getStringExtra("email");

        service = RetrofitClient.getClient().create(ServiceInterface.class);


        preLayout = findViewById(R.id.auth_pre_layout);
        authLayout = findViewById(R.id.auth_layout);
        btnPreAuth = findViewById(R.id.pre_auth_btn);
        btnAuth = findViewById(R.id.auth_btn);
        editAuthCode = findViewById(R.id.auth_code_edit);

        preLayout.setVisibility(View.VISIBLE);
        authLayout.setVisibility(View.GONE);

        btnPreAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preLayout.setVisibility(View.GONE);
                authLayout.setVisibility(View.VISIBLE);
            }
        });

        btnAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAuth();
            }
        });

    }

    private void checkAuth(){
        String userEmail = intentEmail;
        String userAuth = editAuthCode.getText().toString();

        if(userAuth.length()  == 0) {
            Toast.makeText(getApplicationContext(), "인증 코드를 확인해주세요.", Toast.LENGTH_SHORT).show();
        } else{
            startAuth( new AuthData(userEmail, userAuth)  );
        }

    }

    private void startAuth(AuthData data) {
        service.userEmailAuth(data).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(@NonNull Call<AuthResponse> call, @NonNull retrofit2.Response<AuthResponse> response) {

                if(response.isSuccessful()){
                    AuthResponse result = response.body();

                    Toast.makeText(getApplicationContext(),"인증에 성공하였습니다.",Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(AuthActivity.this, MainActivity.class);

                    intent.putExtra("email", intentEmail);
                    intent.putExtra("mode", "LOGIN_USER");

                    startActivity(intent);
                    finish();
                }else{
                    try {
                        String ERROR_MSG = response.errorBody().string();

                        if(ERROR_MSG.equals("{\"errorCode\":\"UNAUTHORIZED_ACCESS\"}")){
                            Toast.makeText(getApplicationContext(),"인증되지 않은 이메일 입니다. 인증해주세요.",Toast.LENGTH_SHORT).show();
                        }else if(ERROR_MSG.equals("{\"errorCode\":\"LOGIN_FAILED_ERROR\"}")){
                            Toast.makeText(getApplicationContext(),"아이디, 비밀번호를 확인해주세요.",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext(),"알 수 없는 에러입니다. 잠시만 기다려주세요.",Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(@NonNull Call<AuthResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show();
                // Log.e("Register : ", t.getMessage());
                //t.printStackTrace();

            }
        });
    }
}