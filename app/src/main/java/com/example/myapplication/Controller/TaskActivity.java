package com.example.myapplication.Controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.Subtask;
import com.example.myapplication.R;
import com.example.myapplication.SharedPref;
import com.example.myapplication.Adapter.SubtaskAdapter;

import java.util.ArrayList;
import java.util.Objects;

public class TaskActivity extends AppCompatActivity {
    public static SubtaskAdapter adapter;

    @SuppressLint("StaticFieldLeak")
    public static ProgressBar progressBar;

    @SuppressLint("StaticFieldLeak")
    private static Context mContext;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        Objects.requireNonNull(getSupportActionBar()).setTitle(MainActivity.currentTask.getName());
        mContext = this;
        
        ArrayList<Subtask> tasks = MainActivity.currentTask.getSubtasks();
        RecyclerView recyclerView = findViewById(R.id.rvSubtasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SubtaskAdapter(this, tasks);
        recyclerView.setAdapter(adapter);

        progressBar = findViewById(R.id.ProgressBar);
        updateProgressBar();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void updateProgressBar() {
        int progress = MainActivity.currentTask.getProgress();
        progressBar.setProgress(progress, true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar2, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void deleteTaskDialog(Subtask st) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder
                .setTitle("Delete Subtask " + '"' + st.getName() + '"' + "?" )
                .setMessage("Are you sure you want to delete this subtask?")
                .setPositiveButton(android.R.string.yes, (dialogInterface, i) -> {
                    TaskActivity.adapter.deleteSubtask(st);
                    Toast.makeText(mContext.getApplicationContext(), "Deleted!", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton(android.R.string.no, (dialogInterface, i) -> dialogInterface.cancel());
        if (SharedPref.currentDark) {
            builder
                    .setTitle(Html.fromHtml("<font color='#FFFFFF'>" + "Delete Task " + '\"' + st.getName() + '\"' + "?" + "</font>"));
        }

        AlertDialog alertDialog = builder.create();
        alertDialog.show();



    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.addSubtaskButton) {

            LayoutInflater li = LayoutInflater.from(getApplicationContext());
            View promptsView = li.inflate(R.layout.addtask_view, null);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(promptsView);

            TextView tv = promptsView.findViewById(R.id.newSubtaskTitle);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,25*MainActivity.textScale);

            EditText et = promptsView.findViewById(R.id.newSubtaskName);
            et.setTextSize(TypedValue.COMPLEX_UNIT_SP,20*MainActivity.textScale);
            if (SharedPref.currentDark) {
                // dark mode config
                tv.setTextColor(Color.parseColor("#FFFFFF"));
                et.setTextColor(Color.parseColor("#FFFFFF"));
                et.setHintTextColor(Color.parseColor("#FFFFFF"));
            } else {
                // light mode config
                tv.setTextColor(Color.parseColor("#000000"));
                et.setTextColor(Color.parseColor("#000000"));
            }

            builder
                    .setCancelable(false)
                    .setPositiveButton("Add", (dialogInterface, i) -> adapter.addSubtask(new Subtask(et.getText().toString())))
                    .setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.cancel());
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }
}
