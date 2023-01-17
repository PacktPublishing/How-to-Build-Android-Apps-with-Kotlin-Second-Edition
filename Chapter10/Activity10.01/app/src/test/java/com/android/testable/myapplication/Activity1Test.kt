package com.android.testable.myapplication

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@MediumTest
@RunWith(AndroidJUnit4::class)
class Activity1Test {

    @Before
    fun setUp() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun `test enter number and submit`() {
        val scenario = ActivityScenario.launch(Activity1::class.java)
        scenario.moveToState(Lifecycle.State.RESUMED)
        onView(withId(R.id.activity_1_edit_text)).perform(replaceText(5.toString()))
        onView(withId(R.id.activity_1_button)).perform(click())
        intended(
            allOf(
                hasComponent(hasShortClassName(".Activity2")),
                hasExtra(Activity2.EXTRA_ITEM_COUNT, 5)
            )
        )
    }
}