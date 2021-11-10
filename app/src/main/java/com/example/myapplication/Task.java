package com.example.myapplication;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.time.temporal.ChronoUnit;

public class Task implements Comparable<Task>{
    private String name;
    private ArrayList<Subtask> subtasks;
    private long daysRemaining;
    private LocalDate dueDate;
    private boolean completed;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Task(String name, LocalDate dueDate) {
        subtasks = new ArrayList<>();
        this.dueDate = dueDate;
        this.name = name;
        this.daysRemaining = ChronoUnit.DAYS.between(LocalDate.now(), this.dueDate);
        this.completed = false;
    }

    public ArrayList<Subtask> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(ArrayList<Subtask> subtasks) {
        this.subtasks = subtasks;
    }

    public long getDaysRemaining() {
        return daysRemaining;
    }

    public void setDaysRemaining(long daysRemaining) {
        this.daysRemaining = daysRemaining;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getDueDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM dd");
        return dueDate.format(formatter);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
        this.setDaysRemaining(Integer.MAX_VALUE);
    }

    @Override
    public int compareTo(Task task) {
        return Long.compare(this.getDaysRemaining(), task.getDaysRemaining());
    }

    @Override
    public String toString() {
        return this.name;
    }

}
