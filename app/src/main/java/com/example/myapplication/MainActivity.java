package com.example.myapplication;

import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    public static TaskAdapter adapter = null;
    public static Task currentTask = null;
    public static int textScale = 1;

    private static Context mContext;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        recyclerView = findViewById(R.id.rvTasks);

        SharedPref.init(getApplicationContext());

        recyclerView = findViewById(R.id.rvTasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<Task> tasks = new ArrayList<>();
        adapter = new TaskAdapter(this, tasks);
        recyclerView.setAdapter(adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onResume() {
        super.onResume();
        String jsonRead = SharedPref.read(SharedPref.TASKS_KEY, SharedPref.DEFAULT_TASKS);

        try {
            List<Task> taskList = SharedPref.jsonStringtoTaskList(jsonRead);
            if (adapter != null) {
                adapter.loadTaskList(taskList);
                adapter.sortTaskList();
                SharedPref.writeToTasks();
                refreshAdapter();
            }

            updateDarkMode();
            updateTextSize();

        } catch (JSONException e) {

            e.printStackTrace();
        }
    }

    private void updateDarkMode() {
        Boolean darkMode = SharedPref.read(SharedPref.DARKMODEKEY, SharedPref.DEFAULT_DARK);
        if (darkMode) {
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM);
        }
    }

    private void toggleDarkMode() {
        Boolean darkMode = SharedPref.read(SharedPref.DARKMODEKEY, SharedPref.DEFAULT_DARK);

        if (darkMode) {
            SharedPref.write(SharedPref.DARKMODEKEY, false);
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM);
        } else {
            SharedPref.write(SharedPref.DARKMODEKEY, true);
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES);
        }
    }

    private void updateTextSize() {
        Boolean largeText = SharedPref.read(SharedPref.LARGETEXTKEY, SharedPref.DEFAULT_LARGE);
        if (largeText) {
            textScale = 2;
        } else {
            textScale = 1;
        }
        refreshAdapter();
    }

    public void refreshAdapter() {
        recyclerView.setAdapter(null);
        recyclerView.setLayoutManager(null);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.notifyDataSetChanged();
    }

    private void toggleTextSize() {
        Boolean largeText = SharedPref.read(SharedPref.LARGETEXTKEY, SharedPref.DEFAULT_LARGE);
        if (largeText) {
            SharedPref.write(SharedPref.LARGETEXTKEY, false);
            textScale = 1;
        } else {
            SharedPref.write(SharedPref.LARGETEXTKEY, true);
            textScale = 2;
        }
        updateTextSize();
    }


    @Override
    protected void onPause() {
        super.onPause();
        SharedPref.writeToTasks();
    }

    public static void deleteTaskDialog(Task t) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        builder
                .setTitle("Delete Task " + '"' + t.getName() + "?" + '"')
                .setMessage("Are you sure you want to delete this task?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MainActivity.adapter.deleteTask(t);
                        Toast.makeText(mContext.getApplicationContext(), "Deleted!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
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
                toggleTextSize();
                break;
            case R.id.darkButton:
                toggleDarkMode();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}