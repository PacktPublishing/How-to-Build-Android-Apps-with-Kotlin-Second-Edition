package com.android.testable.myapplication

import androidx.test.espresso.idling.CountingIdlingResource


class TestItemGenerator(
    private val itemGenerator: ItemGenerator,
    private val countingIdlingResource: CountingIdlingResource
) : ItemGenerator {

    override fun generateItemsAsync(itemCount: Int, callback: (List<Item>) -> Unit) {
        countingIdlingResource.increment()
        itemGenerator.generateItemsAsync(itemCount) {
            callback(it)
            countingIdlingResource.decrement()
        }
    }
}