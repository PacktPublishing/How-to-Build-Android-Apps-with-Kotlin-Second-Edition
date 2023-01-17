package com.android.testable.myapplication

import android.content.res.AssetManager
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.io.InputStream

@RunWith(MockitoJUnitRunner::class)
class AssetFileManagerTest {

    @InjectMocks
    lateinit var assetFileManager: AssetFileManager

    @Mock
    lateinit var assetManager: AssetManager


    @Test
    fun getMyAppFileInputStream() {
        val expected = mock<InputStream>()
        whenever(assetManager.open("my-app-file.txt")).thenReturn(expected)
        val result = assetFileManager.getMyAppFileInputStream()
        Assert.assertEquals(expected, result)
    }
}