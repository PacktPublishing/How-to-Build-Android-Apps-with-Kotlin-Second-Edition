package com.android.testable.myapplication

import androidx.test.espresso.idling.CountingIdlingResource

class TestSynchronizer(
    private val synchronizer: Synchronizer,
    private val countingIdlingResource: CountingIdlingResource
) : Synchronizer {
    override fun executeAfterDelay(callback: (Int) -> Unit) {
        countingIdlingResource.increment()
        synchronizer.executeAfterDelay {
            callback(it)
            countingIdlingResource.decrement()
        }
    }
}