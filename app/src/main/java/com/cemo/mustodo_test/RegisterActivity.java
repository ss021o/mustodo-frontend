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

import com.cemo.mustodo_test.api.RetrofitClient;
import com.cemo.mustodo_test.api.ServiceInterface;
import com.cemo.mustodo_test.api.SignUpData;
import com.cemo.mustodo_test.api.SignUpResponse;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

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
                checkUser();
            }
        });
    }

    private void checkUser(){
        String userEmail = Regi_email.getText().toString();
        String userPassword = Regi_password.getText().toString();
        String userPasswordCheck = Regi_password_check.getText().toString();
        String userNick = Regi_nick.getText().toString();
        Boolean userTermCheck = Regi_terms_check.isChecked();

        Boolean emailCheck, passwordCheck, nickCheck, termCheck = false;

        //????????? ??????
        if (userEmail.length() == 0) {
            Toast.makeText(getApplicationContext(), "???????????? ??????????????????", Toast.LENGTH_SHORT).show();
            emailCheck = false;
        }else if(!isEmailValid(userEmail)){
            Toast.makeText(getApplicationContext(), "@??? ????????? ????????? ???????????? ??????????????????.", Toast.LENGTH_SHORT).show();
            emailCheck = false;
        }else{
            emailCheck = true;
        }

        //????????? ??????
        if (userNick.length() == 0) {
            Toast.makeText(getApplicationContext(), "???????????? ??????????????????", Toast.LENGTH_SHORT).show();
            nickCheck = false;
        }else{
            nickCheck = true;
        }

        //???????????? ??????
        if (userPassword.length() == 0 || userPasswordCheck.length() == 0) {
            Toast.makeText(getApplicationContext(), "??????????????? ??????????????????", Toast.LENGTH_SHORT).show();
            passwordCheck = false;
        } else if (!userPassword.equals(userPasswordCheck)) {
            Toast.makeText(getApplicationContext(), "??????????????? ????????? ?????? ??????????????????." + userPassword + "   " + userPasswordCheck, Toast.LENGTH_SHORT).show();
            passwordCheck = false;
        }else{
            passwordCheck = true;
        }

        //???????????? ??????
        if (userTermCheck == false) {
            Toast.makeText(getApplicationContext(), "??????????????? ??????????????????", Toast.LENGTH_SHORT).show();
            termCheck = false;
        }else{
            termCheck = true;
        }

        if( emailCheck && passwordCheck && nickCheck && termCheck ){
            //startRegister(new SignUpData(userEmail, userNick, userPassword, userPasswordCheck, userTermCheck));
            startRegister(new SignUpData(userEmail, userNick, userPassword));
        }

    }

    private void startRegister(SignUpData data) {
        service.userSignUp(data).enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(@NonNull Call<SignUpResponse> call, @NonNull retrofit2.Response<SignUpResponse> response) {

                if(response.isSuccessful()){
                    SignUpResponse result = response.body();

                    Toast.makeText(getApplicationContext(),"??????????????? ?????????????????????.",Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(RegisterActivity.this, AuthActivity.class);

                    intent.putExtra("email", data.getEmail());
                    intent.putExtra("nickname", data.getNick());


                    startActivity(intent);
                    finish();
                }else{
                    try {
                        String ERROR_MSG = response.errorBody().string();

                        if(ERROR_MSG.equals("{\"errorCode\":\"DUPLICATED_EMAIL\"}")){
                            Toast.makeText(getApplicationContext(),"????????? ??????????????????. ?????? ???????????? ??????????????????.",Toast.LENGTH_SHORT).show();
                            Regi_email.setText("");
                        }else if(ERROR_MSG.equals("{\"errorCode\":\"DUPLICATED_USER_NAME\"}")){
                            Toast.makeText(getApplicationContext(),"????????? ??????????????????. ?????? ???????????? ??????????????????.",Toast.LENGTH_SHORT).show();
                            Regi_nick.setText("");
                        }else {
                            Toast.makeText(getApplicationContext(),"??? ??? ?????? ???????????????. ????????? ??????????????????.",Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(@NonNull Call<SignUpResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "??????????????? ??????????????????.", Toast.LENGTH_SHORT).show();
               // Log.e("Register : ", t.getMessage());
                //t.printStackTrace();

            }
        });
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }
}
