package com.cemo.mustodo_test;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private Frag_home frag_home;
    private Frag_feed frag_feed;
    private Frag_mission frag_mission;
    private Frag_my frag_my;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(Color.parseColor("#ffffff"));

        Intent intent = getIntent();
        String userNick =intent.getExtras().getString("email");


        bottomNavigationView = findViewById(R.id.bottomNavi);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.action_home:
                        setFrag(0);
                        break;
                    case R.id.action_feed:
                        setFrag(1);
                        break;
                    case R.id.action_mission:
                        setFrag(2);
                        break;
                    case R.id.action_my:
                        setFrag(3);
                        break;
                }
                return true;
            }
        });

        frag_home = new Frag_home();
        frag_feed = new Frag_feed();
        frag_mission = new Frag_mission();
        frag_my = new Frag_my();

        setFrag(0);

        Bundle bundle = new Bundle();
        bundle.putString("userNick", userNick);
        frag_home.setArguments(bundle);

    }




    private void setFrag(int n){
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch (n){
            case 0 :
                ft.replace(R.id.main_frame, frag_home);
                ft.commit();
                break;
            case 1 :
                ft.replace(R.id.main_frame, frag_feed);
                ft.commit();
                break;
            case 2 :
                ft.replace(R.id.main_frame, frag_mission);
                ft.commit();
                break;
            case 3 :
                ft.replace(R.id.main_frame, frag_my);
                ft.commit();
                break;
        }
    }

}