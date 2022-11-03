package com.cemo.mustodo_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private EditText LoginEmail;
    private EditText LoginPassword;
    private Button LoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(Color.parseColor("#ffffff"));

        LoginEmail = findViewById(R.id.login_email);
        LoginPassword = findViewById(R.id.login_pass);
        LoginBtn = findViewById(R.id.login_btn);

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = LoginEmail.getText().toString();
                String userPassword = LoginPassword.getText().toString();

                if(userEmail.length()  == 0){
                    Toast.makeText(getApplicationContext(),"이메일을 입력해주세요", Toast.LENGTH_SHORT ).show();
                } else if(userPassword.length()  == 0) {
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                } else{

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("userNick", userEmail);
                    startActivity(intent);

                }


                Response.Listener<String> responseListner = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean isSuccess  = false;

                            if(isSuccess){
                                Toast.makeText(getApplicationContext(),"회원가입에 성공하였습니다.", Toast.LENGTH_SHORT ).show();

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);

                            }else{
                                Toast.makeText(getApplicationContext(),"회원가입에 실패하였습니다.", Toast.LENGTH_SHORT ).show();
                                return;
                            }
                        }catch (Error e) {
                            e.printStackTrace();
                        }catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                };

                LoginRequest loginRequset = new LoginRequest(userEmail, userPassword, responseListner);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequset);

            }
        });

    }
}