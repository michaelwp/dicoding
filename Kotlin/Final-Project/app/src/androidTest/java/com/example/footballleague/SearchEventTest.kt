package com.example.footballleague

import android.view.KeyEvent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.footballleague.activity.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class SearchEventTest {
    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun searchViewBehaviour() {
        onView(withId(R.id.searchViewEvent)).check(matches(isDisplayed()))
        onView(withId(R.id.searchViewEvent)).perform(typeText("spanyol"))
        onView(withId(R.id.searchViewEvent)).perform(pressKey(KeyEvent.KEYCODE_ENTER))
        onView(withId(R.id.list_event_fragment)).check(matches(isDisplayed()))
        Thread.sleep(5000)
        onView(withId(R.id.event_list)).check(matches(isDisplayed()))
        onView(withId(R.id.fav_icon)).perform(click())
        pressBack()
    }
}