package com.example.myapplication;

import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    public static TaskAdapter adapter = null;
    public static Task currentTask = null;
    public static int textScale = 1;

    @SuppressLint("StaticFieldLeak")
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
        boolean darkMode = SharedPref.read(SharedPref.DARKMODEKEY, SharedPref.DEFAULT_DARK);
        if (darkMode) {
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES);
            SharedPref.currentDark = true;
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            SharedPref.currentDark = false;
        }
        refreshAdapter();
    }

    private void toggleDarkMode() {
        boolean darkMode = SharedPref.read(SharedPref.DARKMODEKEY, SharedPref.DEFAULT_DARK);

        if (darkMode) {
            SharedPref.write(SharedPref.DARKMODEKEY, false);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            SharedPref.currentDark = false;
        } else {
            SharedPref.write(SharedPref.DARKMODEKEY, true);
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES);
            SharedPref.currentDark = true;
        }
        refreshAdapter();
    }

    private void updateTextSize() {
        boolean largeText = SharedPref.read(SharedPref.LARGETEXTKEY, SharedPref.DEFAULT_LARGE);
        if (largeText) {
            textScale = 2;
        } else {
            textScale = 1;
        }
        refreshAdapter();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void refreshAdapter() {
        recyclerView.setAdapter(null);
        recyclerView.setLayoutManager(null);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.notifyDataSetChanged();
    }

    private void toggleTextSize() {
        boolean largeText = SharedPref.read(SharedPref.LARGETEXTKEY, SharedPref.DEFAULT_LARGE);
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void deleteTaskDialog(Task t) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        builder
                .setTitle("Delete Task " + '"' + t.getName() + "?" + '"')
                .setMessage("Are you sure you want to delete this task?")
                .setPositiveButton(android.R.string.yes, (dialogInterface, i) -> {
                    MainActivity.adapter.deleteTask(t);
                    Toast.makeText(mContext.getApplicationContext(), "Deleted!", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton(android.R.string.no, (dialogInterface, i) -> dialogInterface.cancel());
        if (SharedPref.currentDark) {
            builder
                    .setTitle(Html.fromHtml("<font color='#FFFFFF'>" + "Delete Task " + '\"' + t.getName() + '\"' + "?" + "</font>"));
        }

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

        if (id == R.id.addTaskButton) {
            Intent i =  new Intent(getApplicationContext(), AddActivity.class);
            startActivity(i);
        }
        else if (id == R.id.textButton) {
            toggleTextSize();
        }
        else if (id == R.id.darkButton) {
            toggleDarkMode();
        }
        return super.onOptionsItemSelected(item);
    }
}