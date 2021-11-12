package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SharedPref {
    private static SharedPreferences mSharedPref;
    public static final String DARKMODEKEY = "DARKMODE";
    public static final boolean DEFAULT_DARK = false;

    public static final String LARGETEXTKEY = "LARGETEXT";
    public static final boolean DEFAULT_LARGE = false;

    public static final String TASKS_KEY = "TASKS";
    public static final String DEFAULT_TASKS = "[{Name: No tasks}]";

    private SharedPref() {

    }

    public static void init(Context context)
    {
        if(mSharedPref == null)
            mSharedPref = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
    }

    public static void write(String key, String value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putString(key, value);
        prefsEditor.apply();
    }

    public static void writeToTasks() {
        String json = new Gson().toJson(MainActivity.adapter.getmData());

        SharedPref.write(SharedPref.TASKS_KEY, json);
    }

    public static String read(String key, String defValue) {
        return mSharedPref.getString(key, defValue);
    }



    public static void write(String key, boolean value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putBoolean(key, value);
        prefsEditor.apply();
    }



    public static Integer read(String key, int defValue) {
        return mSharedPref.getInt(key, defValue);
    }

    public static boolean read(String key, boolean defValue) {
        return mSharedPref.getBoolean(key, defValue);
    }

    public static void write(String key, Integer value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putInt(key, value).apply();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static List<Task> jsonStringtoTaskList(String json_read) throws JSONException {
        JSONArray jsonArray = new JSONArray(json_read);
        List<Task> TaskList = new ArrayList<>();
        // Iterate through task jsonArray
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject indexObject = (JSONObject) jsonArray.get(i);
            LocalDate dueDate = createLocalDateFromJson((JSONObject) indexObject.get("dueDate"));
            Task indexTask = new Task((String) indexObject.get("name"), dueDate);
            // iterate through subtask jsonArray
            // add them ??? FOR SOME REASON ITS NOT WORKING ADJAGKLSJLK
            // it works now pog

            JSONArray subtaskJSONArray = (JSONArray) indexObject.get("subtasks");
            ArrayList<Subtask> subtaskList = new ArrayList<>();


            for (int sub_i = 0; sub_i < subtaskJSONArray.length(); sub_i++) {


                JSONObject subtaskJSONObject = (JSONObject) subtaskJSONArray.get(sub_i);
                Subtask subTaskObject = new Subtask((String) subtaskJSONObject.get("name"));
                subTaskObject.setCompleted(subtaskJSONObject.getBoolean("completed"));
                subtaskList.add(subTaskObject);
                indexTask.addSubTask(subTaskObject);

            }
            indexTask.setSubtasks(subtaskList);
            TaskList.add(indexTask);
        }
        return TaskList;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static LocalDate createLocalDateFromJson(JSONObject dueDate) {
        LocalDate localDate = null;
        String day_string = null;
        String month_string = null;
        String year_string = null;
        try {
            int day = (int) dueDate.get("day");
            int month = (int) dueDate.get("month");
            year_string = dueDate.get("year").toString();

            if (day < 10) {
                day_string = "0" + day;
            } else {
                day_string = Integer.toString(day);
            }
            if (month < 10) {
                month_string = "0" + month;
            } else {
                month_string = Integer.toString(month);
            }
            String localDateString = year_string + "-" + month_string + "-" + day_string;
            localDate = LocalDate.parse(localDateString);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return localDate;
    }





}
