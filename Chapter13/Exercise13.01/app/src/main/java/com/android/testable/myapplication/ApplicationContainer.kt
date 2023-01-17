package com.android.testable.myapplication

import java.util.*

class ApplicationContainer {

    val numberRepository: NumberRepository = NumberRepositoryImpl(Random())
}