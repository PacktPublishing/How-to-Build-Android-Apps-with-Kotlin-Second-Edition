package com.android.testable.myapplication

import androidx.lifecycle.LiveData
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class PreferenceViewModelTest {

    @InjectMocks
    lateinit var preferenceViewModel: PreferenceViewModel

    @Mock
    lateinit var preferenceWrapper: PreferenceWrapper

    @Test
    fun saveText() {
        val text = "text"
        preferenceViewModel.saveText(text)
        verify(preferenceWrapper).saveText(text)
    }

    @Test
    fun getText() {
        val text = mock<LiveData<String>>()
        whenever(preferenceWrapper.getText()).thenReturn(text as LiveData<String>?)
        val result = preferenceWrapper.getText()
        Assert.assertEquals(text, result)
    }
}