package com.android.testable.myapplication

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.concurrent.Executor

@RunWith(MockitoJUnitRunner::class)
class ProviderFileManagerTest {

    @InjectMocks
    lateinit var providerFileManager: ProviderFileManager

    @Mock
    lateinit var context: Context

    @Mock
    lateinit var fileHelper: FileHelper

    @Mock
    lateinit var contentResolver: ContentResolver

    @Mock
    lateinit var executor: Executor

    @Mock
    lateinit var mediaContentHelper: MediaContentHelper

    @Before
    fun setup() {
        doAnswer {
            (it.arguments[0] as Runnable).run()
        }.`when`(executor).execute(any())
    }


    @Test
    fun generatePhotoUri() {
        val root = File("root")
        val picturesFolder = "picturesFolder"
        whenever(fileHelper.getPicturesFolder()).thenReturn(picturesFolder)
        whenever(context.getExternalFilesDir(picturesFolder)).thenReturn(root)
        val time = 10L
        val uri = mock<Uri>()
        whenever(fileHelper.getUriFromFile(File(root, "img_$time.jpg"))).thenReturn(uri)
        val result = providerFileManager.generatePhotoUri(time)
        assertEquals(
            FileInfo(
                uri,
                File(root, "img_$time.jpg"),
                "img_$time.jpg",
                picturesFolder,
                "image/jpeg"
            ), result
        )
    }

    @Test
    fun generateVideoUri() {
        val root = File("root")
        val videosFolder = "videosFolder"
        whenever(fileHelper.getVideosFolder()).thenReturn(videosFolder)
        whenever(context.getExternalFilesDir(videosFolder)).thenReturn(root)
        val time = 10L
        val uri = mock<Uri>()
        whenever(fileHelper.getUriFromFile(File(root, "video_$time.mp4"))).thenReturn(uri)
        val result = providerFileManager.generateVideoUri(time)
        assertEquals(
            FileInfo(
                uri,
                File(root, "video_$time.mp4"),
                "video_$time.mp4",
                videosFolder,
                "video/mp4"
            ), result
        )
    }

    @Test
    fun insertImageToStore() {
        val initialUri = mock<Uri>()
        val fileInfo = mock<FileInfo>()
        val imageContentUri = mock<Uri>()
        val contentValues = mock<ContentValues>()
        val insertedUri = mock<Uri>()
        whenever(fileInfo.uri).thenReturn(initialUri)
        whenever(mediaContentHelper.getImageContentUri()).thenReturn(imageContentUri)
        whenever(mediaContentHelper.generateImageContentValues(fileInfo))
            .thenReturn(contentValues)
        whenever(contentResolver.insert(imageContentUri, contentValues))
            .thenReturn(insertedUri)

        val byteArray = ByteArray(5)
        byteArray[0] = 0
        byteArray[1] = 1
        byteArray[2] = 2
        byteArray[3] = 3
        byteArray[4] = 4
        whenever(contentResolver.openInputStream(initialUri))
            .thenReturn(ByteArrayInputStream(byteArray))
        val outputStream = ByteArrayOutputStream()
        whenever(contentResolver.openOutputStream(insertedUri)).thenReturn(outputStream)

        providerFileManager.insertImageToStore(fileInfo)

        assertArrayEquals(byteArray, outputStream.toByteArray())
    }

    @Test
    fun insertVideoToStore() {
        val initialUri = mock<Uri>()
        val fileInfo = mock<FileInfo>()
        val imageContentUri = mock<Uri>()
        val contentValues = mock<ContentValues>()
        val insertedUri = mock<Uri>()
        whenever(fileInfo.uri).thenReturn(initialUri)
        whenever(mediaContentHelper.getVideoContentUri()).thenReturn(imageContentUri)
        whenever(mediaContentHelper.generateVideoContentValues(fileInfo))
            .thenReturn(contentValues)
        whenever(contentResolver.insert(imageContentUri, contentValues))
            .thenReturn(insertedUri)

        val byteArray = ByteArray(5)
        byteArray[0] = 0
        byteArray[1] = 1
        byteArray[2] = 2
        byteArray[3] = 3
        byteArray[4] = 4
        whenever(contentResolver.openInputStream(initialUri))
            .thenReturn(ByteArrayInputStream(byteArray))
        val outputStream = ByteArrayOutputStream()
        whenever(contentResolver.openOutputStream(insertedUri)).thenReturn(outputStream)

        providerFileManager.insertVideoToStore(fileInfo)

        assertArrayEquals(byteArray, outputStream.toByteArray())
    }

}