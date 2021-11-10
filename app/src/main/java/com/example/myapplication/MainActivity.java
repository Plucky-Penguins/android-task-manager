package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static TaskAdapter adapter = null;
    public static Task currentTask = null;

    private static Context mContext;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        if (adapter == null) {
            // test populating recycle view
            ArrayList<Task> tasks = new ArrayList<>();
            Task t1 = new Task("Fix Spelling", LocalDate.of(2021, Month.NOVEMBER, 7));
            Task t2 = new Task("Do Dishes", LocalDate.of(2022, Month.NOVEMBER, 1));
            Task t3 = new Task("Fix Window", LocalDate.of(2021, Month.DECEMBER, 3));

            ArrayList<Subtask> subtasks = new ArrayList<>();
            Subtask st1 = new Subtask("subtask 1");
            Subtask st2 = new Subtask("subtask 2");
            Subtask st3 = new Subtask("subtask 3");
            subtasks.add(st1);
            subtasks.add(st2);
            subtasks.add(st3);

            t1.setSubtasks(subtasks);
            t2.setSubtasks(subtasks);
            t3.setSubtasks(subtasks);

            RecyclerView recyclerView = findViewById(R.id.rvTasks);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new TaskAdapter(this, tasks);
            recyclerView.setAdapter(adapter);

            adapter.addTask(t3);
            adapter.addTask(t2);
        } else {
            RecyclerView recyclerView = findViewById(R.id.rvTasks);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        }

    }

    public static void taskClicked(int adapterPosition) {
        currentTask = adapter.getTaskAtPosition(adapterPosition);
        Intent i =  new Intent(mContext, TaskActivity.class);
        mContext.startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id) {
            case R.id.addTaskButton:
                Intent i =  new Intent(getApplicationContext(), AddActivity.class);
                startActivity(i);

                break;
            case R.id.textButton:
                //TODO
                break;
            case R.id.darkButton:
                //TODO
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}