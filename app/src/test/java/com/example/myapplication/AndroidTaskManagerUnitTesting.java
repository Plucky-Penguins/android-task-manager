package com.example.myapplication;

import org.json.JSONException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class AndroidTaskManagerUnitTesting {
    Task test_task_1;
    Task test_task_2;

    Subtask test_subtask_1;
    Subtask test_subtask_2;
    ArrayList<Subtask> test_subtask_list;

    LocalDate localDate_1;
    LocalDate localDate_2;

    @Before
    public void setup() {
        // instantiate and create all objects required before a test here
        localDate_1 = LocalDate.parse("2019-12-12");
        localDate_2 = LocalDate.parse("2022-01-01");
        test_task_1 = new Task("task_1", localDate_1);
        test_task_2 = new Task("task_2", localDate_2);
        test_subtask_1 = new Subtask("subtask_1");
        test_subtask_2 = new Subtask("subtask_2");
        test_subtask_list = new ArrayList<>();
        test_subtask_list.add(test_subtask_1);
        test_subtask_list.add(test_subtask_2);
    }

    @After
    public void teardown() {
        // teardown or clear anything after a unit test has been run
        test_subtask_list.clear();
    }

    @Test
    public void test_CreateTask() {
        Task current_task;
        current_task = new Task("current task", LocalDate.parse("2021-01-01"));
        assertEquals("Task", current_task.getClass().getSimpleName());
        assertEquals("current task", current_task.getName());
        assertEquals("01 01", current_task.getDueDate());
        assertEquals(0, current_task.getSubtaskSize());
        assertFalse(current_task.isCompleted());
    }

    @Test
    public void test_failCreateTask() {
        assertThrows(DateTimeParseException.class, () -> new Task("current task",
                LocalDate.parse("01-01-2021")));
    }

    @Test
    public void test_AddGetSubTasksToTask() {
        test_task_1.addSubTask(test_subtask_1);
        test_task_1.addSubTask(test_subtask_2);
        assertTrue(test_task_1.getSubtasks().contains(test_subtask_1));
        assertTrue(test_task_1.getSubtasks().contains(test_subtask_2));
        assertEquals(2, test_task_1.getSubtaskSize());
    }

    @Test
    public void test_EditGetSubTasks() {
        test_task_1.addSubTask(test_subtask_1);
        assertTrue(test_task_1.getSubtasks().contains(test_subtask_1));
        assertFalse(test_task_1.getSubtasks().contains(test_subtask_2));
        assertEquals(1, test_task_1.getSubtaskSize());
        test_task_1.setSubtasks(test_subtask_list);
        assertTrue(test_task_1.getSubtasks().contains(test_subtask_2));
        assertEquals(2, test_task_1.getSubtaskSize());
    }

    @Test
    public void test_GetDaysRemainingFromTask() {
        assertEquals(ChronoUnit.DAYS.between(
                LocalDate.now(), localDate_1),
                test_task_1.getDaysRemaining());
    }

    @Test
    public void test_GetDaysRemainingFromCompletedTask() {
        assertNotEquals(Integer.MAX_VALUE, test_task_1.getDaysRemaining());
        test_task_1.setCompleted(true);
        assertEquals(Integer.MAX_VALUE, test_task_1.getDaysRemaining());
    }

    @Test
    public void test_SetGetTaskName() {
        assertEquals("task_1", test_task_1.getName());
        test_task_1.setName("rename_task_1");
        assertEquals("rename_task_1", test_task_1.getName());
    }

    @Test
    public void test_SetGetTaskCompleted() {
        assertFalse(test_task_1.isCompleted());
        test_task_1.setCompleted(true);
        assertTrue(test_task_1.isCompleted());
    }

    @Test
    public void test_SharedPref_jsonStringtoTaskList_InvalidJson() {
        String invalidJsonArray = "[\"completed\":false," +
                "\"daysRemaining\":1,\"dueDate\":" +
                "{\"day\":12,\"month\":11,\"year\":2021}," +
                "\"name\":\"testing\",\"storedDaysRemaining\":1," +
                "\"subtasks\":" +
                "[{\"completed\":false,\"name\":\"test\"}]}," +
                "{\"completed\":true,\"daysRemaining\":2147483647," +
                "\"dueDate\":{\"day\":12,\"month\":11,\"year\":2021}," +
                "\"name\":\"add task\",\"storedDaysRemaining\":2147483647," +
                "\"subtasks\":[]}";
        assertThrows(JSONException.class, () -> SharedPref.jsonStringtoTaskList(invalidJsonArray));
    }

    @Test
    public void test_SharedPref_jsonStringtoTaskList_EmptyTaskList() {
        String emptyTaskList = "[{}]";
        assertThrows(JSONException.class, () -> SharedPref.jsonStringtoTaskList(emptyTaskList));
    }

    @Test
    public void test_SharedPref_jsonStringtoTaskList_OneTask() throws JSONException {
        String singleTaskList = "[{\"completed\":true," +
                "\"daysRemaining\":2147483647," +
                "\"dueDate\":{\"day\":12,\"month\":11,\"year\":2021}," +
                "\"name\":\"add task\"," +
                "\"storedDaysRemaining\":2147483647," +
                "\"subtasks\":[]}]";
        assertEquals(1, SharedPref.jsonStringtoTaskList(singleTaskList).size());
        assertEquals("add task", SharedPref.jsonStringtoTaskList(singleTaskList)
                .get(0).getName());
        assertEquals("11 12", SharedPref.jsonStringtoTaskList(singleTaskList)
                .get(0).getDueDate());
    }

    @Test
    public void test_SharedPref_createLocalDateFromJson_SingleDigitMonth() throws JSONException {
        String singleTaskList = "[{\"completed\":true," +
                "\"daysRemaining\":-305," +
                "\"dueDate\":{\"day\":12,\"month\":1,\"year\":2021}," +
                "\"name\":\"add task\"," +
                "\"storedDaysRemaining\":-305," +
                "\"subtasks\":[]}]";
        assertEquals(1, SharedPref.jsonStringtoTaskList(singleTaskList).size());
        assertEquals("add task", SharedPref.jsonStringtoTaskList(singleTaskList)
                .get(0).getName());
        assertEquals("01 12", SharedPref.jsonStringtoTaskList(singleTaskList)
                .get(0).getDueDate());
    }

    @Test
    public void test_SharedPref_createLocalDateFromJson_SingleDigitDay() throws JSONException {
        String singleTaskList = "[{\"completed\":true," +
                "\"daysRemaining\":2147483647," +
                "\"dueDate\":{\"day\":1,\"month\":11,\"year\":2021}," +
                "\"name\":\"add task\"," +
                "\"storedDaysRemaining\":2147483647," +
                "\"subtasks\":[]}]";
        assertEquals(1, SharedPref.jsonStringtoTaskList(singleTaskList).size());
        assertEquals("add task", SharedPref.jsonStringtoTaskList(singleTaskList)
                .get(0).getName());
        assertEquals("11 01", SharedPref.jsonStringtoTaskList(singleTaskList)
                .get(0).getDueDate());
    }

    @Test
    public void test_GetTaskProgress() {
        assertEquals(0, test_task_1.getProgress());
        test_task_1.setSubtasks(test_subtask_list);
        assertEquals(0, test_task_1.getProgress());
        test_task_1.getSubtasks().get(0).setCompleted(true);
        assertEquals(50, test_task_1.getProgress());
        test_task_1.getSubtasks().get(1).setCompleted(true);
        assertEquals(100, test_task_1.getProgress());
    }

    @Test
    public void test_CreateSubtask() {
        Subtask current_subtask = new Subtask("current_subtask");
        assertEquals("current_subtask", current_subtask.getName());
        assertFalse(current_subtask.isCompleted());
    }

    @Test
    public void test_SetGetSubtaskName() {
        test_task_1.addSubTask(test_subtask_1);
        test_task_2.addSubTask(test_subtask_2);
        assertEquals("subtask_2", test_task_2.getSubtasks().get(0).getName());
        test_task_2.getSubtasks().get(0).setName("rename_subtask_2");
        assertEquals("rename_subtask_2", test_task_2.getSubtasks().get(0).getName());
    }

    @Test
    public void test_SetGetSubtaskCompletion() {
        test_task_1.addSubTask(test_subtask_1);
        assertFalse(test_task_1.getSubtasks().get(0).isCompleted());
        test_task_1.getSubtasks().get(0).setCompleted(true);
        assertTrue(test_task_1.getSubtasks().get(0).isCompleted());
    }

}