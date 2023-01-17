package com.android.testable.myapplication

class TestRandomizer : Randomizer {
    override fun getTimeToWait(): Int {
        return 1
    }
}
