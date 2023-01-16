package com.android.testable.myapplication

import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

@MediumTest
@Config(shadows = [InstantItemGenerator::class])
@RunWith(AndroidJUnit4::class)
class Activity2Test {

    private val itemCount = 5

    @Before
    fun setUp() {
        Intents.init()
        val scenario = ActivityScenario.launch<Activity2>(
            Activity2.newIntent(
                ApplicationProvider.getApplicationContext(),
                itemCount
            )
        )
        scenario.moveToState(Lifecycle.State.RESUMED)
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @LooperMode(LooperMode.Mode.PAUSED)
    @Test
    fun `test click opens activity 3`() {
        val position = 3
        val itemText = ApplicationProvider.getApplicationContext<MyApplication>()
            .getString(R.string.item_x, (position + 1))
        onView(withText(itemText)).check(matches(isDisplayed()))
        onView(withId(R.id.activity_2_recycler_view)).perform(
            scrollToPosition<RecyclerView.ViewHolder>(
                position
            )
        )
        onView(withId(R.id.activity_2_recycler_view)).perform(
            actionOnItemAtPosition<RecyclerView.ViewHolder>(
                position,
                click()
            )
        )
        intended(
            allOf(
                hasComponent(hasShortClassName(".Activity3")),
                hasExtra(Activity3.EXTRA_ITEM, Item(itemText))
            )
        )

    }
}