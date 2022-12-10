package com.cemo.mustodo_test;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cemo.mustodo_test.api.ServiceInterface;
import com.cemo.mustodo_test.api.todo.TodoServiceInterface;

public class Frag_mission extends Fragment {
    private View view;

    private ServiceInterface service;
    private TodoServiceInterface todoService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_mission, null);

        return  view;
    }
}
