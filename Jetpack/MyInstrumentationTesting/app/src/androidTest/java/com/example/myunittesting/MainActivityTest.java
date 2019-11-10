package com.example.myunittesting;


import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private String dummyVolume = "504.0";
    private String dummyCircumference = "2016.0";
    private String dummySurfaceArea = "396.0";
    private String dummyLength = "12.0";
    private String dummyWidth = "7.0";
    private String dummyHeight = "6.0";
    private String emptyInput = "";
    private String fieldEmpty = "Field ini tidak boleh kosong";

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    public void setValue(String l, String w, String h) {
        onView(withId(R.id.edt_length)).perform(typeText(l), closeSoftKeyboard());
        onView(withId(R.id.edt_width)).perform(typeText(w), closeSoftKeyboard());
        onView(withId(R.id.edt_height)).perform(typeText(h), closeSoftKeyboard());
    }

    public void saveValue() {
        onView(withId(R.id.btn_save)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_save)).perform(click());
    }

    public void setAction(int buttonAction) {
        onView(withId(buttonAction)).check(matches(isDisplayed()));
        onView(withId(buttonAction)).perform(click());
    }

    public void checkResult(String resValue) {
        onView(withId(R.id.tv_result)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_result)).check(matches(ViewMatchers.withText(resValue)));
    }

    public void checkError(int textField, String errMsg) {
        onView(withId(textField)).check(matches(ViewMatchers.hasErrorText(errMsg)));
    }

    @Test
    public void getCircumference() {
        setValue(dummyLength, dummyWidth, dummyHeight);
        saveValue();
        setAction(R.id.btn_calculate_circumference);
        checkResult(dummyCircumference);
    }

    @Test
    public void getSurfaceArea() {
        setValue(dummyLength, dummyWidth, dummyHeight);
        saveValue();
        setAction(R.id.btn_calculate_surface_area);
        checkResult(dummySurfaceArea);
    }

    @Test
    public void getVolume() {
        setValue(dummyLength, dummyWidth, dummyHeight);
        saveValue();
        setAction(R.id.btn_calculate_volume);
        checkResult(dummyVolume);
    }

    @Test
    public void checkIfEmpty() {
        setValue(emptyInput, emptyInput, emptyInput);
        saveValue();
        checkError(R.id.edt_length, fieldEmpty);

        setValue(dummyLength, emptyInput, emptyInput);
        saveValue();
        checkError(R.id.edt_width, fieldEmpty);

        setValue(emptyInput, dummyWidth, emptyInput);
        saveValue();
        checkError(R.id.edt_height, fieldEmpty);

        setValue(emptyInput, emptyInput, dummyHeight);
        saveValue();
    }

}