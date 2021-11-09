package com.example.myapplication;

public class Subtask {
    private String name;
    private boolean completed;

    public Subtask(String name) {
        this.name = name;
        completed = false;
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
    }
}
