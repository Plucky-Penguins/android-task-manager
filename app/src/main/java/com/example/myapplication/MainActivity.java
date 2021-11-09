package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TaskAdapter adapter;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Task> tasks = new ArrayList<>();
        Task t1 = new Task("Fix Spelling", LocalDate.of(2021, Month.DECEMBER, 7));
        Task t2 = new Task("Do Dishes", LocalDate.of(2022, Month.JANUARY, 1));
        Task t3 = new Task("Fix Window", LocalDate.of(2021, Month.DECEMBER, 3));

        tasks.add(t1);
        tasks.add(t2);
        tasks.add(t3);

        RecyclerView recyclerView = findViewById(R.id.rvTasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TaskAdapter(this, tasks);
        recyclerView.setAdapter(adapter);
    }
}