package com.cemo.mustodo_test;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.cemo.mustodo_test.api.RetrofitClient;
import com.cemo.mustodo_test.api.ServiceInterface;
import com.cemo.mustodo_test.api.diary.DiaryServiceInterface;
import com.cemo.mustodo_test.api.popular.PopularResponse;
import com.cemo.mustodo_test.api.popular.PopularServiceInterface;
import com.cemo.mustodo_test.api.todo.OpenResponse;
import com.cemo.mustodo_test.api.todo.TodoDayData;
import com.cemo.mustodo_test.api.todo.TodoServiceInterface;
import com.cemo.mustodo_test.popular.ImageSliderAdapter;
import com.cemo.mustodo_test.popular.MsgAdapter;
import com.cemo.mustodo_test.popular.MsgData;
import com.cemo.mustodo_test.todo.OpenTodoAdaper;
import com.cemo.mustodo_test.todo.TodoData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Frag_mission extends Fragment {
    private View view;

    private ServiceInterface service;
    private TodoServiceInterface todoService;
    private PopularServiceInterface popularService;

    private ViewPager2 sliderViewPager;
    private LinearLayout layoutIndicator;

    ArrayList<MsgData> msgDataArrayList = new ArrayList<MsgData>();
    MsgAdapter msgAdapter;

    private ListView msg_list_view;


    private String[] images = new String[] {
            "https://sa.subox.co.kr/data/editor/2212/352c12c566732153979f035028b936b5_1670679919_2958.png",
            "https://sa.subox.co.kr/data/editor/2212/352c12c566732153979f035028b936b5_1670679919_4737.png",
            "https://sa.subox.co.kr/data/editor/2212/352c12c566732153979f035028b936b5_1670679919_5427.png",
            "https://sa.subox.co.kr/data/editor/2212/352c12c566732153979f035028b936b5_1670679919_6075.png",
            "https://sa.subox.co.kr/data/editor/2212/352c12c566732153979f035028b936b5_1670679919_6695.png"
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_mission, null);

        popularService = RetrofitClient.getClient().create(PopularServiceInterface.class);

        sliderViewPager = view.findViewById(R.id.sliderViewPager);
        layoutIndicator = view.findViewById(R.id.layoutIndicators);

        msgAdapter = new MsgAdapter(view.getContext(), msgDataArrayList);
        msg_list_view = view.findViewById(R.id.msg_list_view);

        sliderViewPager.setOffscreenPageLimit(1);
        sliderViewPager.setAdapter(new ImageSliderAdapter(getContext(), images));

        sliderViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentIndicator(position);
            }
        });

        setupIndicators(images.length);

        getTodayMsg();
        getAllMsg();

        return  view;
    }


    private void getTodayMsg(){
        try {
            popularService.getTodayFamous().enqueue(new Callback<PopularResponse>() {
                @Override
                public void onResponse(Call<PopularResponse> call, Response<PopularResponse> response) {
                    if (response.isSuccessful()) {
                        PopularResponse res = response.body();

                        if(res != null){
                            List<MsgData> ja = res.getData();

                            for (int i=0; i< ja.size(); i++){
                                TextView today_msg_text = view.findViewById(R.id.today_msg_text);
                                TextView today_msg_talker = view.findViewById(R.id.today_msg_talker);

                                today_msg_text.setText(ja.get(i).getContents());
                                today_msg_talker.setText("- "+ja.get(i).getTalker());
                            }

                        }
                    } else {
                        try {

                        } catch (Error e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<PopularResponse> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }catch (Error e){
            e.printStackTrace();
        }
    }

    public void drawAllMsg(List<MsgData> item){
        if (item != null) {
            try {
                if(msgDataArrayList != null) msgDataArrayList.clear();
                for (int i = 0; i < item.size(); i++) {
                    MsgData dataItem = item.get(i);

                    int id = dataItem.getId();
                    String contents = dataItem.getContents();
                    String talker = dataItem.getTalker();
                    int likeCount = dataItem.getLike();

                    msgDataArrayList.add(new MsgData(id, contents,  talker, likeCount));
                    msgAdapter.notifyDataSetChanged();
                }

                ViewGroup.LayoutParams params = msg_list_view.getLayoutParams();
                params.height = 550 * msgAdapter.getCount();
                msg_list_view.setLayoutParams(params);

                msg_list_view.setAdapter(msgAdapter);


            }catch (Error e){
                e.printStackTrace();
            }

        } else {

        }
    }

    private void getAllMsg(){
        try {
            popularService.getFamousAll().enqueue(new Callback<PopularResponse>() {
                @Override
                public void onResponse(Call<PopularResponse> call, Response<PopularResponse> response) {
                    if (response.isSuccessful()) {
                        PopularResponse res = response.body();

                        if(res != null){
                            List<MsgData> ja = res.getData();

                            drawAllMsg(ja);
                        }
                    } else {
                        try {

                        } catch (Error e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<PopularResponse> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }catch (Error e){
            e.printStackTrace();
        }
    }


    private void setupIndicators(int count) {
        ImageView[] indicators = new ImageView[count];
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        params.setMargins(16, 8, 16, 8);

        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(getContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(getContext(),
                    R.drawable.bg_indicator_inactive));
            indicators[i].setLayoutParams(params);
            layoutIndicator.addView(indicators[i]);
        }
        setCurrentIndicator(0);
    }

    private void setCurrentIndicator(int position) {
        int childCount = layoutIndicator.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) layoutIndicator.getChildAt(i);
            if (i == position) {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        getContext(),
                        R.drawable.bg_indicator_active
                ));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        getContext(),
                        R.drawable.bg_indicator_inactive
                ));
            }
        }
    }


}
