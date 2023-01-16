package com.android.testable.myapplication

import com.android.testable.myapplication.NumberRepository
import java.util.*

class NumberRepositoryImpl(private val random: Random) : NumberRepository {

    override fun generateNextNumber(): Int {
        return random.nextInt()
    }
}