package com.android.testable.myapplication

import android.app.Application
import java.util.*

open class MyApplication : Application() {

    lateinit var synchronizer: Synchronizer

    override fun onCreate() {
        super.onCreate()
        synchronizer = createSynchronizer()

    }

    open fun createRandomizer(): Randomizer = RandomizerImpl(Random())

    open fun createSynchronizer(): Synchronizer = SynchronizerImpl(createRandomizer(), Timer())
}