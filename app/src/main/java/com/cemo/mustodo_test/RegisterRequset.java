package com.cemo.mustodo_test;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequset extends StringRequest {
    final static private String Test_URL = "https://127.0.0.1:8080/user";

    private Map<String, String> map;

    public RegisterRequset(String userEmail, String userPassword, String userPasswordCheck, String userName, boolean termCheck, Response.Listener<String> listener){
        super(Method.POST, Test_URL , listener, null);

        map = new HashMap<>();
        map.put("email", userEmail);
        map.put("name", userName);
        map.put("password", userPassword);
        map.put("passwordConfirm", userPasswordCheck);
        map.put("isTermsAndCondition", String.valueOf(termCheck));
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
