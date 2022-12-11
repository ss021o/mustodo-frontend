package com.cemo.mustodo_test;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cemo.mustodo_test.api.RetrofitClient;
import com.cemo.mustodo_test.api.UserData;
import com.cemo.mustodo_test.api.UserResponse;
import com.cemo.mustodo_test.api.todo.StatsMonthResponse;
import com.cemo.mustodo_test.api.todo.StatsResponse;
import com.cemo.mustodo_test.api.todo.TodoMonthData;
import com.cemo.mustodo_test.api.todo.TodoServiceInterface;
import com.cemo.mustodo_test.popular.MsgData;
import com.cemo.mustodo_test.stats.StatAdapter;
import com.cemo.mustodo_test.stats.StatData;
import com.cemo.mustodo_test.stats.StatMonthData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatActivity extends AppCompatActivity {

    String userNick, userEmail, mode, selColor;
    ImageButton stat_month_prev, stat_month_next;

    TextView stat_user_nick, stat_month_text;

    GridView statMonthView;

    StatAdapter statAdapter;
    ArrayList<StatMonthData> statMonthDataArrayList = new ArrayList<StatMonthData>();

    private TodoServiceInterface todoService;

    int LDay, total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(Color.parseColor("#ffffff"));

        todoService = RetrofitClient.getClient().create(TodoServiceInterface.class);

        userNick = getIntent().getStringExtra("userNick");
        userEmail = getIntent().getStringExtra("userEmail");
        mode = getIntent().getStringExtra("mode");

        stat_user_nick = findViewById(R.id.stat_user_nick);
        stat_month_text = findViewById(R.id.stat_month_text);

        stat_user_nick.setText(userNick+"님의 기록");

        statMonthView = (GridView) findViewById(R.id.gridview);

        stat_month_prev = findViewById(R.id.stat_month_prev);
        stat_month_next = findViewById(R.id.stat_month_next);


        statAdapter = new StatAdapter(this, statMonthDataArrayList);

        statMonthView.setAdapter(statAdapter);

        LDay = getCurrentEndDateOfMonth();

        drawMonthStats(LDay);

        stat_month_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    getPrevYearMonth(stat_month_text.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        stat_month_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    getNextYearMonth(stat_month_text.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void drawMonthStats(int lastday){
        if(statMonthDataArrayList != null) statMonthDataArrayList.clear();
        for(int i = 0; i<lastday; i++){
            String thisdate;
            thisdate = String.valueOf(i+1);
            statMonthDataArrayList.add(new StatMonthData(thisdate, total,0));
        }
        statAdapter.notifyDataSetChanged();
    }

    public void getPrevYearMonth(String thisMonth) throws ParseException {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy년 MM월");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        SimpleDateFormat month = new SimpleDateFormat("MM");
        SimpleDateFormat day = new SimpleDateFormat("d");

        Date date = inputFormat.parse(thisMonth);

        String formattedYear = year.format(date);
        String formattedMonth = month.format(date);
        //String formattedDay = month.format(day);

        if(Integer.valueOf(formattedMonth) == 1 ){
            formattedMonth = "12";
            formattedYear = String.valueOf( Integer.valueOf(formattedYear) - 1);
        }else{
            formattedMonth = String.valueOf( Integer.valueOf(formattedMonth) - 1);
        }

        Calendar cal = Calendar.getInstance();

        cal.set( Integer.valueOf(formattedYear) , Integer.valueOf(formattedMonth) -1, 1);

        int lastDate = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        String format = formattedYear + "-"+ formattedMonth;

        getMonthTotalStats(userNick, format);
        getMonthCheckedStats(userNick, format);

        drawMonthStats(lastDate);

        stat_month_text.setText(formattedYear+"년 " + formattedMonth+"월");

    }


    public void getNextYearMonth(String thisMonth) throws ParseException {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy년 MM월");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        SimpleDateFormat month = new SimpleDateFormat("MM");
        SimpleDateFormat day = new SimpleDateFormat("d");

        Date date = inputFormat.parse(thisMonth);

        String formattedYear = year.format(date);
        String formattedMonth = month.format(date);
        //String formattedDay = month.format(day);

        if(Integer.valueOf(formattedMonth) == 12 ){
            formattedMonth = "1";
            formattedYear = String.valueOf( Integer.valueOf(formattedYear) + 1);
        }else{
            formattedMonth = String.valueOf( Integer.valueOf(formattedMonth) + 1);
        }

        Calendar cal = Calendar.getInstance();

        cal.set( Integer.valueOf(formattedYear) , Integer.valueOf(formattedMonth) -1, 1);

        int lastDate = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        String format = formattedYear + "-"+ formattedMonth;

        getMonthTotalStats(userNick, format);
        getMonthCheckedStats(userNick, format);

        drawMonthStats(lastDate);

        stat_month_text.setText(formattedYear+"년 " + formattedMonth+"월");

    }


    public int getCurrentEndDateOfMonth(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String format = sdf.format(cal.getTime());

        getMonthTotalStats(userNick, format);
        getMonthCheckedStats(userNick, format);

        int LastDay = cal.getTime().getDate();

        return LastDay;
    }

    public void getMonthCheckedStats(String userNick, String date){
        try {
            todoService.getTodoMonthCheckedStats(userNick, date).enqueue(new Callback<StatsMonthResponse>() {
                @Override
                public void onResponse(Call<StatsMonthResponse> call, Response<StatsMonthResponse> response) {
                    if(response.isSuccessful()) {
                        StatsMonthResponse monthResponse = response.body();
                        if(monthResponse != null){
                            if(monthResponse.getCode() == 200){
                                List<StatData> ja = monthResponse.getData();

                                if(ja != null){
                                    for (int i=0; i<ja.size(); i++) {
                                        StatData dataItem = ja.get(i);

                                        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                                        //SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
                                        SimpleDateFormat outputFormat2 = new SimpleDateFormat("d");

                                        Date date = null;
                                        try {
                                            date = inputFormat.parse(dataItem.getTodoDate());
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                        String formattedDate = outputFormat2.format(date);

                                        if(statMonthDataArrayList != null){
                                            statMonthDataArrayList.get(Integer.valueOf(formattedDate)).setChecked(dataItem.getChecked());
                                        }

                                        statAdapter.notifyDataSetChanged();

                                    }
                                }


                            }else {

                            }
                        }
                    }else{
                        try {

                        }catch (Error e){
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<StatsMonthResponse> call, Throwable t) {

                }
            });
        }catch (Error e){
            e.printStackTrace();
        }

    }

    public void getMonthTotalStats(String userNick, String date){
        try {
            todoService.getTodoMonthTotalStats(userNick, date).enqueue(new Callback<StatsMonthResponse>() {
                @Override
                public void onResponse(Call<StatsMonthResponse> call, Response<StatsMonthResponse> response) {
                    if(response.isSuccessful()) {
                        StatsMonthResponse monthResponse = response.body();
                        if(monthResponse.getCode() == 200){
                            List<StatData> ja = monthResponse.getData();

                            if(ja != null){
                                for (int i=0; i<ja.size(); i++) {
                                    StatData dataItem = ja.get(i);

                                    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                                    SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
                                    SimpleDateFormat outputFormat2 = new SimpleDateFormat("d");

                                    Date date = null;
                                    try {
                                        date = inputFormat.parse(dataItem.getTodoDate());
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    String formattedDate = outputFormat2.format(date);

                                    if(statMonthDataArrayList != null){
                                        statMonthDataArrayList.get(Integer.valueOf(formattedDate)).setTotal(dataItem.getTotal());
                                    }

                                    statAdapter.notifyDataSetChanged();

                                }
                            }

                        }else {

                        }

                    } else {
                        System.out.println("sd");
                    };
                }

                @Override
                public void onFailure(Call<StatsMonthResponse> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }catch (Error e){
            e.printStackTrace();
        }

    }
}