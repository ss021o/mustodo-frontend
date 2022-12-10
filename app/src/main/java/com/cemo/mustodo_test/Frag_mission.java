package com.cemo.mustodo_test;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.cemo.mustodo_test.api.ServiceInterface;
import com.cemo.mustodo_test.api.todo.TodoServiceInterface;
import com.cemo.mustodo_test.popular.ImageSliderAdapter;

public class Frag_mission extends Fragment {
    private View view;

    private ServiceInterface service;
    private TodoServiceInterface todoService;

    private ViewPager2 sliderViewPager;
    private LinearLayout layoutIndicator;


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

        sliderViewPager = view.findViewById(R.id.sliderViewPager);
        layoutIndicator = view.findViewById(R.id.layoutIndicators);

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

        return  view;
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
