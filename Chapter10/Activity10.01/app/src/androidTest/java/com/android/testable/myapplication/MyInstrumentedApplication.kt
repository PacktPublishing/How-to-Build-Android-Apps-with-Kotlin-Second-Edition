package com.android.testable.myapplication

import androidx.test.espresso.idling.CountingIdlingResource

class MyInstrumentedApplication : MyApplication() {

    val countingIdlingResource = CountingIdlingResource("Timer resource")

    override fun createItemGenerator(): ItemGenerator {
        return TestItemGenerator(
            ItemGeneratorImpl(timer, StringProvider(this), 0),
            countingIdlingResource
        )
    }
}