package com.cemo.mustodo_test;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private EditText Regi_email;
    private EditText Regi_nick;
    private EditText Regi_password;
    private EditText Regi_password_check;
    private CheckBox Regi_terms_check;

    private Button Regi_btn;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(Color.parseColor("#ffffff"));

        Regi_email = (EditText) findViewById(R.id.regi_email);
        Regi_nick = (EditText) findViewById(R.id.regi_nick);
        Regi_password = (EditText) findViewById(R.id.regi_pass);
        Regi_password_check = (EditText) findViewById(R.id.regi_pass_check);
        Regi_terms_check = (CheckBox) findViewById(R.id.regi_terms_check);

        Regi_btn = (Button) findViewById(R.id.regi_btn);

        Regi_btn.setBackgroundResource(R.color.gray);
        Regi_btn.setTextColor(R.color.black);

        Regi_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String userEmail = Regi_email.getText().toString();
                String userPassword = Regi_password.getText().toString();
                String userPasswordCheck = Regi_password_check.getText().toString();
                String userNick = Regi_nick.getText().toString();
                Boolean userTermCheck = Regi_terms_check.isChecked();

                if(userEmail.length()  == 0){
                    Toast.makeText(getApplicationContext(),"이메일을 입력해주세요", Toast.LENGTH_SHORT ).show();
                } else if(userNick.length()  == 0) {
                    Toast.makeText(getApplicationContext(), "닉네임을 입력해주세요", Toast.LENGTH_SHORT).show();
                } else if(userPassword.length()  == 0|| userPasswordCheck.length()  == 0){
                    Toast.makeText(getApplicationContext(),"비밀번호를 입력해주세요", Toast.LENGTH_SHORT ).show();
                } else if(!userPassword.equals(userPasswordCheck)){
                    Toast.makeText(getApplicationContext(),"비밀번호가 맞는지 다시 확인해주세요." +userPassword +"   "+userPasswordCheck, Toast.LENGTH_SHORT ).show();
                } else if(userTermCheck == false){
                    Toast.makeText(getApplicationContext(),"이용약관을 동의해주세요", Toast.LENGTH_SHORT ).show();
                }else{
                    Regi_btn.setBackgroundResource(R.color.main);
                    Regi_btn.setTextColor(R.color.white);

                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    intent.putExtra("userNick", userNick);
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

                                //Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                //startActivity(intent);

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

                RegisterRequset registerRequset = new RegisterRequset(userEmail, userPassword, userPasswordCheck, userNick, userTermCheck, responseListner);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequset);


            }
        });


    }
}