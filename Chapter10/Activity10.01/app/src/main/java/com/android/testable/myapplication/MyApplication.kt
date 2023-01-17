package com.android.testable.myapplication

import android.app.Application
import java.util.*

open class MyApplication : Application() {

    val timer = Timer()
    lateinit var stringProvider: StringProvider
    lateinit var itemGenerator: ItemGenerator

    override fun onCreate() {
        super.onCreate()
        stringProvider = StringProvider(this)
        itemGenerator = createItemGenerator()
    }

    protected open fun createItemGenerator(): ItemGenerator =
        ItemGeneratorImpl(timer, stringProvider, 1000)
}