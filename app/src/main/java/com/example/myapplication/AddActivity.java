package com.example.myapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AddActivity extends AppCompatActivity {
    int selectedDayOfMonth = 0;
    int selectedMonth = 0;
    int selectedYear = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        getSupportActionBar().setTitle("Add Task");

        findViewById(R.id.addNewTaskButton).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                addTask();
            }
        });

        EditText et = findViewById(R.id.editTextTaskName);
        et.setTextSize(TypedValue.COMPLEX_UNIT_SP,15*MainActivity.textScale);

        Button bt = findViewById(R.id.addNewTaskButton);
        bt.setTextSize(TypedValue.COMPLEX_UNIT_SP,15*MainActivity.textScale);

        CalendarView cv = findViewById(R.id.calendarView);
        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                selectedDayOfMonth = i2;
                selectedYear = i;
                selectedMonth = i1+1;
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        // update json with added task
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addTask() {
        EditText taskNameView = (EditText) findViewById(R.id.editTextTaskName);
        String taskName = taskNameView.getText().toString();

        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);
        Long dateLong = calendarView.getDate();
        LocalDate date = Instant.ofEpochMilli(dateLong).atZone(ZoneId.systemDefault()).toLocalDate();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedString = date.format(formatter);

        if (selectedDayOfMonth == 0) {
            selectedDayOfMonth = date.getDayOfMonth();
        }

        if (selectedYear == 0) {
            selectedYear = date.getYear();
        }

        if (selectedMonth == 0) {
            selectedMonth = Integer.parseInt(date.getMonth().toString());
        }

        if (taskName.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Error: Specify the task name", Toast.LENGTH_SHORT).show();
        } else {
            Task newTask = new Task(taskName, LocalDate.of(selectedYear, Month.of(selectedMonth), selectedDayOfMonth));
            MainActivity.adapter.addTask(newTask);
            SharedPref.writeToTasks();
            Intent i =  new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }
    }
}
