package com.example.myapplication.Model;

import androidx.annotation.NonNull;

public class Subtask implements Comparable<Subtask>{
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

    @NonNull
    @Override
    public String toString() {
        return this.name + " | " + this.completed;
    }

    @Override
    public int compareTo(Subtask subtask) {
        return 0;
    }

    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        final Subtask other = (Subtask) obj;
        return other.getName().equals(this.getName());
    }
}
