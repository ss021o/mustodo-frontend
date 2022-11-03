package com.cemo.mustodo_test;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {
    final static private String Test_URL = "http://192.168.0.93:8080/api/auth/login";

    //http://192.168.0.93:8080/api/auth/sign-up

    private Map<String, String> map;

    public LoginRequest(String userEmail, String userPassword,  Response.Listener<String> listener){
        super(Method.POST, Test_URL , listener, null);

        map = new HashMap<>();
        map.put("email", userEmail);
        map.put("password", userPassword);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
