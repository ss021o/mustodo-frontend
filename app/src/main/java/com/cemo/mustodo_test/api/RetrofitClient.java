package com.cemo.mustodo_test.api;

import android.util.Log;

import java.net.CookieManager;
import java.net.CookiePolicy;

import okhttp3.CookieJar;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClient {
    private final static String BASE_URL = "http://192.168.0.3:8080/api/";
    private static Retrofit retrofit = null;

    private RetrofitClient() {
    }

    public static Retrofit getClient() {
        if (retrofit == null) {

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            CookieManager cookieManager = new CookieManager();
            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

            OkHttpClient client = new OkHttpClient.Builder()
//                    .addInterceptor(interceptor)
                    .cookieJar(new JavaNetCookieJar(cookieManager)).build();


            Log.d("Register : ","initMyAPI : " + BASE_URL);


            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL) // 요청을 보낼 base url을 설정한다.
                    .addConverterFactory(new NullOnEmptyConverterFactory())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create()) // JSON 파싱을 위한 GsonConverterFactory를 추가한다.
                    .client(client)
                    .build();
        }

        return retrofit;
    }
}