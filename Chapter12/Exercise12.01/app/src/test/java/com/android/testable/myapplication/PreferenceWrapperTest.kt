package com.android.testable.myapplication

import android.content.SharedPreferences
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.inOrder
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class PreferenceWrapperTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()
    private lateinit var preferenceWrapper: PreferenceWrapper

    @Mock
    lateinit var sharedPreferences: SharedPreferences

    @Mock
    lateinit var editor: SharedPreferences.Editor

    @Before
    fun setUp() {
        val text = "text"
        whenever(sharedPreferences.getString(KEY_TEXT, "")).thenReturn(text)
        whenever(sharedPreferences.registerOnSharedPreferenceChangeListener(any()))
            .thenAnswer {
                (it.arguments[0] as SharedPreferences.OnSharedPreferenceChangeListener).onSharedPreferenceChanged(
                    sharedPreferences,
                    KEY_TEXT
                )
            }

        preferenceWrapper = PreferenceWrapper(sharedPreferences)
        val result = preferenceWrapper.getText()
        Assert.assertEquals(text, result.value!!)

        whenever(sharedPreferences.edit()).thenReturn(editor)
        whenever(editor.putString(any(), any())).thenReturn(editor)
    }

    @Test
    fun saveText() {
        val text = "text"
        preferenceWrapper.saveText(text)
        val inOrder = inOrder(sharedPreferences, editor)
        inOrder.verify(sharedPreferences).edit()
        inOrder.verify(editor).putString(KEY_TEXT, text)
        inOrder.verify(editor).apply()
    }

    @Test
    fun getText() {
        val text = "text"
        whenever(sharedPreferences.getString(KEY_TEXT, "")).thenReturn(text)
        val result = preferenceWrapper.getText()
        Assert.assertEquals(text, result.value!!)
    }
}