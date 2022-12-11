package com.cemo.mustodo_test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.cemo.mustodo_test.feed.Frag_feed;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private Frag_home frag_home;
    private Frag_feed frag_feed;
    private Frag_mission frag_mission;
    private Frag_my frag_my;


    private LinearLayout header_menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(Color.parseColor("#ffffff"));

        Intent intent = getIntent();

        String email = intent.getExtras().getString("email");
        String mode = intent.getExtras().getString("mode");
        //checkUserInfo(new UserData(email));

        //Toast.makeText(getApplicationContext(),data.getEmail(), Toast.LENGTH_SHORT ).show();

        header_menu = findViewById(R.id.header_menu);

        bottomNavigationView = findViewById(R.id.bottomNavi);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.action_home:
                        setFrag(0);
                        header_menu.setVisibility(View.VISIBLE);
                        break;
                    case R.id.action_feed:
                        setFrag(1);
                        header_menu.setVisibility(View.VISIBLE);
                        break;
                    case R.id.action_mission:
                        setFrag(2);
                        header_menu.setVisibility(View.VISIBLE);
                        break;
                    case R.id.action_my:
                        setFrag(3);
                        header_menu.setVisibility(View.GONE);
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

        bundle.putString("mode", mode);
        bundle.putString("userEmail", email);

        frag_home.setArguments(bundle);
        frag_my.setArguments(bundle);



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