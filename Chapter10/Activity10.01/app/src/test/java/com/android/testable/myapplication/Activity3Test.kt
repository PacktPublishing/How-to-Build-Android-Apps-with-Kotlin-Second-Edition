package com.android.testable.myapplication

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@MediumTest
@RunWith(AndroidJUnit4::class)
class Activity3Test {

    private val item = Item("Text to display")

    @Before
    fun setUp() {
        val scenario = ActivityScenario.launch<Activity3>(
            Activity3.newIntent(
                ApplicationProvider.getApplicationContext(),
                item
            )
        )
        scenario.moveToState(Lifecycle.State.RESUMED)
    }

    @Test
    fun `test displays correct text`() {
        onView(withId(R.id.activity_3_text_view)).check(
            matches(
                withText(
                    ApplicationProvider.getApplicationContext<MyApplication>().getString(
                        R.string.you_clicked_y,
                        item.text
                    )
                )
            )
        )
    }
}