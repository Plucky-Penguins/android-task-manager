package com.example.myapplication;

import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.rxjava2.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava2.RxDataStore;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

<<<<<<< HEAD
import org.json.JSONObject;

=======
import java.lang.reflect.Array;
>>>>>>> basic-ui
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static TaskAdapter adapter = null;
    public static Task currentTask = null;

    private static Context mContext;
    public static boolean darkMode = false;

    private static ArrayList<Task> tasks = new ArrayList<>();


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        if (adapter == null) {
            // test populating recycle view
            Task t1 = new Task("Fix Spelling", LocalDate.of(2021, Month.NOVEMBER, 7));
            Task t2 = new Task("Do Dishes", LocalDate.of(2022, Month.NOVEMBER, 1));
            Task t3 = new Task("Fix Window", LocalDate.of(2021, Month.DECEMBER, 3));
            tasks.add(t1);
            tasks.add(t2);
            tasks.add(t3);

            ArrayList<Subtask> subtasks = new ArrayList<>();
            Subtask st1 = new Subtask("subtask 1");
            Subtask st2 = new Subtask("subtask 2");
            Subtask st3 = new Subtask("subtask 3");
            subtasks.add(st1);
            subtasks.add(st2);
            subtasks.add(st3);

            ArrayList<Subtask> subtasks2 = new ArrayList<>();
            subtasks2.add(new Subtask("hello"));
            subtasks2.add(new Subtask("world"));

            t1.setSubtasks(subtasks);
            t2.setSubtasks(subtasks);
            t3.setSubtasks(subtasks2);

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

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * Write the list of objects to a json file
     */
    public void writeToJson() {
        JSONObject json = new JSONObject();
        for (Task task_name : tasks) {

        }
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
                //TODO
                break;
            case R.id.darkButton:
                //TODO
                if (darkMode) {
                    darkMode = false;
                    AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM);
                } else {
                    darkMode = true;
                    AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES);
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}