package com.android.testable.myapplication.storage.filesystem

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileOutputStream

@RunWith(MockitoJUnitRunner::class)
class ProviderFileHandlerTest {

    @get:Rule
    var folder = TemporaryFolder()

    @InjectMocks
    lateinit var providerFileHandler: ProviderFileHandler

    @Mock
    lateinit var context: Context

    @Mock
    lateinit var fileToUriMapper: FileToUriMapper

    @Mock
    lateinit var contentResolver: ContentResolver

    @Test
    fun writeStream() {
        val name = "name"
        val rootFolder = folder.newFolder()
        whenever(context.externalCacheDir).thenReturn(rootFolder)
        val fileToSave = File(rootFolder, name)
        whenever(context.contentResolver).thenReturn(contentResolver)
        val uri = mock<Uri>()
        whenever(fileToUriMapper.getUriForFile(context, fileToSave)).thenReturn(uri)
        whenever(contentResolver.openOutputStream(uri, "rw"))
            .thenReturn(FileOutputStream(fileToSave))

        val inputStream =
            ByteArrayInputStream(arrayOf(1.toByte(), 2.toByte(), 3.toByte()).toByteArray())

        providerFileHandler.writeStream(name, inputStream)

        Assert.assertEquals(3, fileToSave.length().toInt())
    }
}