package com.cemo.mustodo_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

            }
        });

    }
}