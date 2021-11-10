package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

public class TaskActivity extends AppCompatActivity {
    public static SubtaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        getSupportActionBar().setTitle(MainActivity.currentTask.getName());
        
        ArrayList<Subtask> tasks = MainActivity.currentTask.getSubtasks();
        RecyclerView recyclerView = findViewById(R.id.rvSubtasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SubtaskAdapter(this, tasks);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar2, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.addSubtaskButton) {

        }
        return super.onOptionsItemSelected(item);
    }
}
