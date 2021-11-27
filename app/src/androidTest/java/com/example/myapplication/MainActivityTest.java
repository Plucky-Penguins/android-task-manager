package com.example.myapplication;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
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
