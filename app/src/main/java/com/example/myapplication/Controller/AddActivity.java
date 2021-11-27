package com.example.myapplication.Controller;

import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Controller.MainActivity;
import com.example.myapplication.Model.Task;
import com.example.myapplication.R;
import com.example.myapplication.SharedPref;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Objects;

public class AddActivity extends AppCompatActivity {
    int selectedDayOfMonth = 0;
    int selectedMonth = 0;
    int selectedYear = 0;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Add Task");

        findViewById(R.id.addNewTaskButton).setOnClickListener(view -> addTask());

        EditText et = findViewById(R.id.editTextTaskName);
        et.setTextSize(TypedValue.COMPLEX_UNIT_SP,15* MainActivity.textScale);

        Button bt = findViewById(R.id.addNewTaskButton);
        bt.setTextSize(TypedValue.COMPLEX_UNIT_SP,15*MainActivity.textScale);

        CalendarView cv = findViewById(R.id.calendarView);
        cv.setOnDateChangeListener((calendarView, i, i1, i2) -> {
            selectedDayOfMonth = i2;
            selectedYear = i;
            selectedMonth = i1+1;
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addTask() {
        EditText taskNameView = findViewById(R.id.editTextTaskName);
        String taskName = taskNameView.getText().toString();

        CalendarView calendarView = findViewById(R.id.calendarView);
        long dateLong = calendarView.getDate();
        LocalDate date = Instant.ofEpochMilli(dateLong).atZone(ZoneId.systemDefault()).toLocalDate();

        if (taskName.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Error: Specify the task name", Toast.LENGTH_SHORT).show();
        } else {
            if (selectedDayOfMonth == 0) {
                selectedDayOfMonth = date.getDayOfMonth();
            }

            if (selectedYear == 0) {
                selectedYear = date.getYear();
            }

            if (selectedMonth == 0) {
                selectedMonth = date.getMonth().getValue();
            }

            Task newTask = new Task(taskName, LocalDate.of(selectedYear, Month.of(selectedMonth), selectedDayOfMonth));
            MainActivity.adapter.addTask(newTask);
            SharedPref.writeToTasks();

            finish();
        }
    }
}
