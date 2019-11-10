package com.example.footballleague

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.footballleague.activity.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class DataTeamTest {

    @Rule
    @JvmField
    var activityRule = ActivityTestRule<MainActivity>(MainActivity::class.java)


    @Test
    fun onPickLeague() {
        Thread.sleep(5000)
        onView(withId(R.id.league_list_recycler))
            .check(matches(isDisplayed()))

        onView(withId(R.id.league_list_recycler))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    click()
                )
            )

        onView(withId(R.id.team_navigation)).check(matches(isDisplayed()))
        onView(withId(R.id.team_navigation)).perform(click())

        Thread.sleep(5000)
        onView(withId(R.id.team_list_recycler)).check(matches(isDisplayed()))


    }


}