package com.android.testable.myapplication

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import java.math.BigInteger

@RunWith(MockitoJUnitRunner::class)
class NumberAdderErrorHandlingTest {

    @InjectMocks
    lateinit var numberAdder: NumberAdder

    @Test(expected = NumberAdder.InvalidNumberException::class)
    fun sum() {
        val input = -1
        val callback = mock<(BigInteger) -> Unit>()

        numberAdder.sum(input, callback)
    }
}