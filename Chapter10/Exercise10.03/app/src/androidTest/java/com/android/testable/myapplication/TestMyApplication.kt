package com.android.testable.myapplication

import androidx.test.espresso.idling.CountingIdlingResource

class TestMyApplication : MyApplication() {

    val countingIdlingResource = CountingIdlingResource("Timer resource")

    override fun createRandomizer(): Randomizer {
        return TestRandomizer()
    }

    override fun createSynchronizer(): Synchronizer {
        return TestSynchronizer(super.createSynchronizer(), countingIdlingResource)
    }
}
