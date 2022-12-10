package com.cemo.mustodo_test.feed;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.cemo.mustodo_test.R;
import com.cemo.mustodo_test.api.RetrofitClient;
import com.cemo.mustodo_test.api.sns.SnsServiceInterface;
import com.cemo.mustodo_test.api.sns.TodoFeedDto;
import com.cemo.mustodo_test.api.sns.TodoFeedResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Frag_feed extends Fragment {

    private View view;
    private SnsServiceInterface service;
    private int page;
    private RecyclerView todoFeedView;
    private TodoFeedAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_feed, null);

        todoFeedView = view.findViewById(R.id.lvTodoFeed);
        service = RetrofitClient.getClient().create(SnsServiceInterface.class);

        service.getTodoFeed(page).enqueue(new Callback<TodoFeedResponse>() {
            @Override
            public void onResponse(Call<TodoFeedResponse> call, Response<TodoFeedResponse> response) {
                if (response.isSuccessful()) {
                    List<TodoFeedDto> todoFeed = response.body().getTodoFeed();
                    adapter = new TodoFeedAdapter(todoFeed);
                    todoFeedView.setAdapter(adapter);
                } else {
                    Log.d("response", "fail");
                }
            }

            @Override
            public void onFailure(Call<TodoFeedResponse> call, Throwable t) {

            }
        });
        return  view;
    }
}
