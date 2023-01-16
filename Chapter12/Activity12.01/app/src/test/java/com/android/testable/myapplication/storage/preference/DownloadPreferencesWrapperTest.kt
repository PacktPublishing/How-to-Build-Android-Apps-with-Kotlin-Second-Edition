package com.android.testable.myapplication.storage.preference

import android.content.SharedPreferences
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
class DownloadPreferencesWrapperTest {

    @InjectMocks
    lateinit var downloadPreferencesWrapper: DownloadPreferencesWrapper

    @Mock
    lateinit var sharedPreferences: SharedPreferences

    @Test
    fun getNumberOfResults() {
        val expected = 12
        whenever(sharedPreferences.getInt(KEY_NR_RESULTS, DEFAULT_NO_OF_RESULTS)).thenReturn(
            expected
        )

        val result = downloadPreferencesWrapper.getNumberOfResults()

        Assert.assertEquals(expected, result)
    }

    @Test
    fun saveNumberOfResults() {
        val expected = 12
        val editor = mock<SharedPreferences.Editor>()
        whenever(sharedPreferences.edit()).thenReturn(editor)
        whenever(editor.putInt(KEY_NR_RESULTS, expected)).thenReturn(editor)

        downloadPreferencesWrapper.saveNumberOfResults(expected)

        verify(editor).apply()
    }
}