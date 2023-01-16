package com.android.testable.myapplication

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.Executor


@RunWith(MockitoJUnitRunner::class)
class ProviderFileManagerTest {

    @get:Rule
    var folder = TemporaryFolder()

    @InjectMocks
    lateinit var providerFileManager: ProviderFileManager
    @Mock
    lateinit var context: Context
    @Mock
    lateinit var fileToUriMapper: FileToUriMapper
    @Mock
    lateinit var executor: Executor
    @Mock
    lateinit var uri: Uri
    @Mock
    lateinit var contextResolver: ContentResolver
    private lateinit var rootFolder: File


    @Before
    fun setUp() {
        whenever(executor.execute(any())).thenAnswer {
            (it.arguments[0] as Runnable).run()
        }
        rootFolder = folder.newFolder()
        whenever(context.getExternalFilesDir(null)).thenReturn(rootFolder)
        whenever(context.contentResolver).thenReturn(contextResolver)

    }

    @Test
    fun writeStream() {
        val outputFileName = "name"
        val docsFolder = File(rootFolder, "docs")
        whenever(fileToUriMapper.getUriFromFile(context, File(docsFolder, outputFileName)))
            .thenReturn(uri)
        val outputFile = folder.newFile("outputfile")
        whenever(contextResolver.openOutputStream(uri, "rw"))
            .thenReturn(FileOutputStream(outputFile))
        val byteArray = ByteArray(5)
        byteArray[0] = 0
        byteArray[1] = 1
        byteArray[2] = 2
        byteArray[3] = 3
        byteArray[4] = 4
        providerFileManager.writeStream(outputFileName, ByteArrayInputStream(byteArray))

        Assert.assertEquals(byteArray.size, outputFile.length().toInt())
    }
}