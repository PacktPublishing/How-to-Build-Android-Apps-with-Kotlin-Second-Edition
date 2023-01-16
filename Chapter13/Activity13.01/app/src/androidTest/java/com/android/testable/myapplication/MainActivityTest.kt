package com.android.testable.myapplication

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Test
    fun testDisplaysPosts() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)
        scenario.moveToState(Lifecycle.State.RESUMED)
        onView(withText("Title 1")).check(matches(isDisplayed()))
        onView(withText("Body 1")).check(matches(isDisplayed()))
        onView(withText("Title 2")).check(matches(isDisplayed()))
        onView(withText("Body 2")).check(matches(isDisplayed()))
        onView(withText("Title 3")).check(matches(isDisplayed()))
        onView(withText("Body 3")).check(matches(isDisplayed()))
    }
}