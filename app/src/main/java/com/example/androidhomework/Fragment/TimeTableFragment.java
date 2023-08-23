package com.example.androidhomework.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.androidhomework.Adapter.RecyclerViewAdapter2;
import com.example.androidhomework.R;

public class TimeTableFragment extends Fragment {

    private View mainView;
    private Context context;

    Button buttonYesterday;
    Button buttonTomorrow;
    Button buttonToday;
    RecyclerView recyclerView;
    RecyclerViewAdapter2 recyclerViewAdapter2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_time_table, container, false);
        context = mainView.getContext();

        buttonTomorrow = mainView.findViewById(R.id.buttonTomorrow);
        buttonYesterday = mainView.findViewById(R.id.buttonYesterday);
        buttonToday = mainView.findViewById(R.id.buttonToday);
        recyclerView = mainView.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        try {
            recyclerViewAdapter2 = new RecyclerViewAdapter2(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        recyclerView.setAdapter(recyclerViewAdapter2);
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));

        buttonYesterday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewAdapter2.yesterday();
                try {
                    recyclerViewAdapter2 = new RecyclerViewAdapter2(context);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                recyclerView.setAdapter(recyclerViewAdapter2);
            }
        });
        buttonTomorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewAdapter2.tomorrow();
                try {
                    recyclerViewAdapter2 = new RecyclerViewAdapter2(context);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                recyclerView.setAdapter(recyclerViewAdapter2);
            }
        });
        buttonToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewAdapter2.today();
                try {
                    recyclerViewAdapter2 = new RecyclerViewAdapter2(context);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                recyclerView.setAdapter(recyclerViewAdapter2);
            }
        });
        return mainView;
    }
}