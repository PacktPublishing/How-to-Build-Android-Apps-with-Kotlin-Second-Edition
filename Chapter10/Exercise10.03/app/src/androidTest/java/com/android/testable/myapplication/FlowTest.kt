package com.android.testable.myapplication

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class FlowTest {

    private val myApplication = getApplicationContext<TestMyApplication>()

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(myApplication.countingIdlingResource)
        val scenario = ActivityScenario.launch(Activity1::class.java)
        scenario.moveToState(Lifecycle.State.RESUMED)
    }

    @Test
    fun verifyFlow() {
        onView(withId(R.id.activity_1_button)).perform(click())
        onView(withId(R.id.activity_2_text_view)).check(
            matches(
                withText(
                    myApplication.getString(
                        R.string.opened_after_x_seconds,
                        1
                    )
                )
            )
        )
    }
}
