package com.example.myapplication;

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

    @Override
    public String toString() {
        return this.name + " | " + this.completed;
    }

    @Override
    public int compareTo(Subtask subtask) {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        final Subtask other = (Subtask) obj;
        if (other.getName().equals(this.getName())) {
            return true;
        }

        return false;
    }
}
