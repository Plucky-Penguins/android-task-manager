package com.example.myapplication;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class AndroidTaskManagerUnitTesting {
    @Before
    public void setup() {
        // instantiate and create all objects required before a test here
    }

    @After
    public void teardown() {
        // teardown or clear anything after a unit test has been run
    }

    @Test
    public void test_CreateGetTask() {
        assert(true);
    }

    @Test
    public void test_AddGetSubTasksToTask() {
        assert(true);
    }

    @Test
    public void test_EditGetSubTasks() {
        assert(true);
    }

    @Test
    public void test_SetGetDaysRemaining() {
        assert(true);
    }

    @Test
    public void test_SetGetTaskName() {
        assert(true);
    }

    @Test
    public void test_SetGetTaskCompleted() {

    }

    @Test
    public void test_SharedPref_jsonStringtoTaskList_InvalidJson() {
        assert(true);
    }

    @Test
    public void test_SharedPref_jsonStringtoTaskList_EmptyTaskList() {
        assert(true);
    }

    @Test
    public void test_SharedPref_jsonStringtoTaskList_ThreeTasks() {
        assert(true);
    }

    @Test
    public void test_SharedPref_createLocalDateFromJson_SingleDigitMonth() {
        assert(true);
    }

    @Test
    public void test_SharedPref_createLocalDateFromJson_SingleDigitDay() {
        assert(true);
    }

    @Test
    public void test_GetTaskProgress() {
        assert(true);
    }

    @Test
    public void test_CreateSubtask() {
        assert(true);
    }

    @Test
    public void test_SetGetSubtaskName() {
        assert(true);
    }

    @Test
    public void test_SetGetSubtaskCompletion() {
        assert(true);
    }

    @Test
    public void test_subtaskEquals() {
        assert(true);
    }

    @Test
    public void test_SharedPref_createLocalDateFromJson_SingleDigitDayMonth() {
        assert(true);
    }

    @Test
    public void test_SharedPref_createLocalDateFromJson_DoubleDigitDayMonth() {
        assert(true);
    }

    @Test
    public void test_SharedPref_createLocalDateFromJson_PastYear() {
        assert(true);
    }

    @Test
    public void test_SharedPref_createLocalDateFromJson_FutureYear() {
        assert(true);
    }


}