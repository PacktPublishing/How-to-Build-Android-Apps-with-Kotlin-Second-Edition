package com.android.testable.myapplication

import android.app.Application

class RandomApplication : Application() {

    val applicationContainer = ApplicationContainer()

    override fun onCreate() {
        super.onCreate()
    }
}