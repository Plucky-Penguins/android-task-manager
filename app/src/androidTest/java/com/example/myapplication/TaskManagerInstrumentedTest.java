package com.example.myapplication;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import com.example.myapplication.Controller.MainActivity;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class TaskManagerInstrumentedTest {

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.myapplication", appContext.getPackageName());
    }

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testUIOpenTaskCreateMenu() {
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();
        onView(withContentDescription(R.string.addtaskbutton)).perform(click());
        onView(withId(R.id.editTextTaskName)).check(matches(isDisplayed()));
        onView(withId(R.id.editTextTaskName)).perform(typeText(""));
        onView(withId(R.id.addNewTaskButton)).check(matches(isDisplayed()));
    }

    @Test
    public void testUICreateTaskAddSubTask() {
        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.addTaskButton), withContentDescription("addTaskButton"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                2),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.editTextTaskName),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.editTextTaskName),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("Add task"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.editTextTaskName), withText("Add task"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText3.perform(pressImeActionButton());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.addNewTaskButton), withText("Add"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.rvTasks),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                0)));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction actionMenuItemView2 = onView(
                allOf(withId(R.id.addSubtaskButton), withContentDescription("addSubtaskButton"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView2.perform(click());

        ViewInteraction editText = onView(
                allOf(withId(R.id.newSubtaskName),
                        childAtPosition(
                                allOf(withId(R.id.addTaskLinearLayout),
                                        childAtPosition(
                                                withId(R.id.custom),
                                                0)),
                                1),
                        isDisplayed()));
        editText.perform(replaceText("test"), closeSoftKeyboard());

        ViewInteraction materialButton2 = onView(
                allOf(withId(android.R.id.button1), withText("Add"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonPanel),
                                        0),
                                3)));
        materialButton2.perform(scrollTo(), click());

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.rvSubtasks),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                0)));
        recyclerView2.perform(actionOnItemAtPosition(0, click()));
    }

    @Test
    public void testUICreateAndDeleteTask() {
        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.addTaskButton), withContentDescription("addTaskButton"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                2),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.editTextTaskName),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("Add task"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.editTextTaskName), withText("Add task"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText2.perform(pressImeActionButton());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.addNewTaskButton), withText("Add"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.rvTasks),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                0)));
        recyclerView.perform(actionOnItemAtPosition(0, longClick()));

        ViewInteraction materialButton2 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonPanel),
                                        0),
                                3)));
        materialButton2.perform(scrollTo(), click());
    }

    @Test
    public void testUICreateMultipleTasks() {
        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.addTaskButton), withContentDescription("addTaskButton"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                2),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.editTextTaskName),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("Task 1"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.editTextTaskName), withText("Task 1"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText2.perform(pressImeActionButton());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.editTextTaskName), withText("Task 1"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText3.perform(pressImeActionButton());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.addNewTaskButton), withText("Add"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction actionMenuItemView2 = onView(
                allOf(withId(R.id.addTaskButton), withContentDescription("addTaskButton"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                2),
                        isDisplayed()));
        actionMenuItemView2.perform(click());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.editTextTaskName),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText("Task 2"), closeSoftKeyboard());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.editTextTaskName), withText("Task 2"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText5.perform(pressImeActionButton());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.addNewTaskButton), withText("Add"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        materialButton2.perform(click());
    }

    @Test
    public void testUICreateDeleteMultipleSubTask() {
        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.addTaskButton), withContentDescription("addTaskButton"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                2),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.editTextTaskName),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("Add task"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.editTextTaskName), withText("Add task"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText2.perform(pressImeActionButton());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.addNewTaskButton), withText("Add"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.rvTasks),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                0)));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction actionMenuItemView2 = onView(
                allOf(withId(R.id.addSubtaskButton), withContentDescription("addSubtaskButton"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView2.perform(click());

        ViewInteraction editText = onView(
                allOf(withId(R.id.newSubtaskName),
                        childAtPosition(
                                allOf(withId(R.id.addTaskLinearLayout),
                                        childAtPosition(
                                                withId(R.id.custom),
                                                0)),
                                1),
                        isDisplayed()));
        editText.perform(replaceText("add subtask"), closeSoftKeyboard());

        ViewInteraction materialButton2 = onView(
                allOf(withId(android.R.id.button1), withText("Add"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonPanel),
                                        0),
                                3)));
        materialButton2.perform(scrollTo(), click());

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.rvSubtasks),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                0)));
        recyclerView2.perform(actionOnItemAtPosition(0, click()));

        pressBack();

        ViewInteraction recyclerView3 = onView(
                allOf(withId(R.id.rvTasks),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                0)));
        recyclerView3.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction actionMenuItemView3 = onView(
                allOf(withId(R.id.addSubtaskButton), withContentDescription("addSubtaskButton"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView3.perform(click());

        ViewInteraction editText2 = onView(
                allOf(withId(R.id.newSubtaskName),
                        childAtPosition(
                                allOf(withId(R.id.addTaskLinearLayout),
                                        childAtPosition(
                                                withId(R.id.custom),
                                                0)),
                                1),
                        isDisplayed()));
        editText2.perform(replaceText("add subtask2"), closeSoftKeyboard());

        ViewInteraction materialButton3 = onView(
                allOf(withId(android.R.id.button1), withText("Add"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonPanel),
                                        0),
                                3)));
        materialButton3.perform(scrollTo(), click());

        pressBack();

        ViewInteraction recyclerView4 = onView(
                allOf(withId(R.id.rvTasks),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                0)));
        recyclerView4.perform(actionOnItemAtPosition(0, longClick()));

        ViewInteraction materialButton4 = onView(
                allOf(withId(android.R.id.button2), withText("Cancel"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonPanel),
                                        0),
                                2)));
        materialButton4.perform(scrollTo(), click());

        ViewInteraction recyclerView5 = onView(
                allOf(withId(R.id.rvTasks),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                0)));
        recyclerView5.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction recyclerView6 = onView(
                allOf(withId(R.id.rvSubtasks),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                0)));
        recyclerView6.perform(actionOnItemAtPosition(1, longClick()));

        ViewInteraction materialButton5 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonPanel),
                                        0),
                                3)));
        materialButton5.perform(scrollTo(), click());

        ViewInteraction recyclerView7 = onView(
                allOf(withId(R.id.rvSubtasks),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                0)));
        recyclerView7.perform(actionOnItemAtPosition(0, longClick()));

        ViewInteraction materialButton6 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonPanel),
                                        0),
                                3)));
        materialButton6.perform(scrollTo(), click());

        pressBack();

        ViewInteraction recyclerView8 = onView(
                allOf(withId(R.id.rvTasks),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                0)));
        recyclerView8.perform(actionOnItemAtPosition(0, longClick()));

        ViewInteraction materialButton7 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonPanel),
                                        0),
                                3)));
        materialButton7.perform(scrollTo(), click());
    }

    @Test
    public void testUICompleteMultiSubTask() {
        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.addTaskButton), withContentDescription("addTaskButton"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                2),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.editTextTaskName),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("createmultisubtask"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.editTextTaskName), withText("createmultisubtask"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText2.perform(pressImeActionButton());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.addNewTaskButton), withText("Add"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.rvTasks),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                0)));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction actionMenuItemView2 = onView(
                allOf(withId(R.id.addSubtaskButton), withContentDescription("addSubtaskButton"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView2.perform(click());

        ViewInteraction editText = onView(
                allOf(withId(R.id.newSubtaskName),
                        childAtPosition(
                                allOf(withId(R.id.addTaskLinearLayout),
                                        childAtPosition(
                                                withId(R.id.custom),
                                                0)),
                                1),
                        isDisplayed()));
        editText.perform(replaceText("1"), closeSoftKeyboard());

        ViewInteraction materialButton2 = onView(
                allOf(withId(android.R.id.button1), withText("Add"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonPanel),
                                        0),
                                3)));
        materialButton2.perform(scrollTo(), click());

        ViewInteraction actionMenuItemView3 = onView(
                allOf(withId(R.id.addSubtaskButton), withContentDescription("addSubtaskButton"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView3.perform(click());

        ViewInteraction editText2 = onView(
                allOf(withId(R.id.newSubtaskName),
                        childAtPosition(
                                allOf(withId(R.id.addTaskLinearLayout),
                                        childAtPosition(
                                                withId(R.id.custom),
                                                0)),
                                1),
                        isDisplayed()));
        editText2.perform(replaceText("2"), closeSoftKeyboard());

        ViewInteraction materialButton3 = onView(
                allOf(withId(android.R.id.button1), withText("Add"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonPanel),
                                        0),
                                3)));
        materialButton3.perform(scrollTo(), click());

        ViewInteraction actionMenuItemView4 = onView(
                allOf(withId(R.id.addSubtaskButton), withContentDescription("addSubtaskButton"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView4.perform(click());

        ViewInteraction editText3 = onView(
                allOf(withId(R.id.newSubtaskName),
                        childAtPosition(
                                allOf(withId(R.id.addTaskLinearLayout),
                                        childAtPosition(
                                                withId(R.id.custom),
                                                0)),
                                1),
                        isDisplayed()));
        editText3.perform(replaceText("3"), closeSoftKeyboard());

        ViewInteraction materialButton4 = onView(
                allOf(withId(android.R.id.button1), withText("Add"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonPanel),
                                        0),
                                3)));
        materialButton4.perform(scrollTo(), click());

        ViewInteraction actionMenuItemView5 = onView(
                allOf(withId(R.id.addSubtaskButton), withContentDescription("addSubtaskButton"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView5.perform(click());

        ViewInteraction editText4 = onView(
                allOf(withId(R.id.newSubtaskName),
                        childAtPosition(
                                allOf(withId(R.id.addTaskLinearLayout),
                                        childAtPosition(
                                                withId(R.id.custom),
                                                0)),
                                1),
                        isDisplayed()));
        editText4.perform(click());

        ViewInteraction editText5 = onView(
                allOf(withId(R.id.newSubtaskName),
                        childAtPosition(
                                allOf(withId(R.id.addTaskLinearLayout),
                                        childAtPosition(
                                                withId(R.id.custom),
                                                0)),
                                1),
                        isDisplayed()));
        editText5.perform(replaceText("4"), closeSoftKeyboard());

        ViewInteraction materialButton5 = onView(
                allOf(withId(android.R.id.button1), withText("Add"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonPanel),
                                        0),
                                3)));
        materialButton5.perform(scrollTo(), click());

        pressBack();

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.rvTasks),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                0)));
        recyclerView2.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction recyclerView3 = onView(
                allOf(withId(R.id.rvSubtasks),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                0)));
        recyclerView3.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction recyclerView4 = onView(
                allOf(withId(R.id.rvSubtasks),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                0)));
        recyclerView4.perform(actionOnItemAtPosition(1, click()));

        ViewInteraction recyclerView5 = onView(
                allOf(withId(R.id.rvSubtasks),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                0)));
        recyclerView5.perform(actionOnItemAtPosition(2, click()));

        ViewInteraction recyclerView6 = onView(
                allOf(withId(R.id.rvSubtasks),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                0)));
        recyclerView6.perform(actionOnItemAtPosition(3, click()));

        pressBack();

        ViewInteraction recyclerView7 = onView(
                allOf(withId(R.id.rvTasks),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                0)));
        recyclerView7.perform(actionOnItemAtPosition(0, longClick()));

        ViewInteraction materialButton6 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonPanel),
                                        0),
                                3)));
        materialButton6.perform(scrollTo(), click());
    }

    @Test
    public void testUICreateTaskDeleteDarkMode() {
        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.darkButton), withContentDescription("darkButton"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction actionMenuItemView2 = onView(
                allOf(withId(R.id.addTaskButton), withContentDescription("addTaskButton"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                2),
                        isDisplayed()));
        actionMenuItemView2.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.editTextTaskName),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("dark mode"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.editTextTaskName), withText("dark mode"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText2.perform(pressImeActionButton());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.addNewTaskButton), withText("Add"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.rvTasks),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                0)));
        recyclerView.perform(actionOnItemAtPosition(0, longClick()));

        ViewInteraction materialButton2 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonPanel),
                                        0),
                                3)));
        materialButton2.perform(scrollTo(), click());

        ViewInteraction actionMenuItemView3 = onView(
                allOf(withId(R.id.darkButton), withContentDescription("darkButton"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView3.perform(click());
    }

    @Test
    public void testUICreateTaskLargeText() {
        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.textButton), withContentDescription("textButton"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                1),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction actionMenuItemView2 = onView(
                allOf(withId(R.id.addTaskButton), withContentDescription("addTaskButton"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                2),
                        isDisplayed()));
        actionMenuItemView2.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.editTextTaskName),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("Large Text"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.editTextTaskName), withText("Large Text"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText2.perform(pressImeActionButton());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.addNewTaskButton), withText("Add"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction actionMenuItemView3 = onView(
                allOf(withId(R.id.addTaskButton), withContentDescription("addTaskButton"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                2),
                        isDisplayed()));
        actionMenuItemView3.perform(click());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.editTextTaskName),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("Large Text 2"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.editTextTaskName), withText("Large Text 2"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText4.perform(pressImeActionButton());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.addNewTaskButton), withText("Add"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        materialButton2.perform(click());

        actionMenuItemView = onView(
                allOf(withId(R.id.textButton), withContentDescription("textButton"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                1),
                        isDisplayed()));
        actionMenuItemView.perform(click());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}