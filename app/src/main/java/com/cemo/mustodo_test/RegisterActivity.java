package com.cemo.mustodo_test;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
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
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.cemo.mustodo_test.api.RetrofitClient;
import com.cemo.mustodo_test.api.ServiceInterface;
import com.cemo.mustodo_test.api.SignUpData;
import com.cemo.mustodo_test.api.SignUpResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    private EditText Regi_email;
    private EditText Regi_nick;
    private EditText Regi_password;
    private EditText Regi_password_check;
    private CheckBox Regi_terms_check;

    private Button Regi_btn;

    private ServiceInterface service;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(Color.parseColor("#ffffff"));

        service = RetrofitClient.getClient().create(ServiceInterface.class);

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

                if (userEmail.length() == 0) {
                    Toast.makeText(getApplicationContext(), "이메일을 입력해주세요", Toast.LENGTH_SHORT).show();
                } else if (userNick.length() == 0) {
                    Toast.makeText(getApplicationContext(), "닉네임을 입력해주세요", Toast.LENGTH_SHORT).show();
                } else if (userPassword.length() == 0 || userPasswordCheck.length() == 0) {
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                } else if (!userPassword.equals(userPasswordCheck)) {
                    Toast.makeText(getApplicationContext(), "비밀번호가 맞는지 다시 확인해주세요." + userPassword + "   " + userPasswordCheck, Toast.LENGTH_SHORT).show();
                } else if (userTermCheck == false) {
                    Toast.makeText(getApplicationContext(), "이용약관을 동의해주세요", Toast.LENGTH_SHORT).show();
                }else {
                    startRegister(new SignUpData(userEmail, userNick, userPassword, userPasswordCheck, userTermCheck));
                }

            }
        });
    }

    private void startRegister(SignUpData data) {
        service.userSignUp(data).enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(@NonNull Call<SignUpResponse> call, @NonNull retrofit2.Response<SignUpResponse> response) {
                Log.d("Register : ", "??");
                if(response.isSuccessful()){
                    SignUpResponse result = response.body();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"??",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<SignUpResponse> call, Throwable t) {
                Log.d("Register : ", "여긴가?");
                Toast.makeText(getApplicationContext(), "에러", Toast.LENGTH_SHORT).show();
                Log.e("Register : ", t.getMessage());
                t.printStackTrace();

            }
        });
    }
}
