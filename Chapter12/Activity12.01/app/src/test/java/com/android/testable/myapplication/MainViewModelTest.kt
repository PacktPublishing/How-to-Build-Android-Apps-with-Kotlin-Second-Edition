package com.android.testable.myapplication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.android.testable.myapplication.repository.DogUi
import com.android.testable.myapplication.repository.DownloadRepository
import com.android.testable.myapplication.repository.Result

import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @InjectMocks
    lateinit var mainViewModel: MainViewModel

    @Mock
    lateinit var downloadRepository: DownloadRepository

    @Test
    fun getDogs() {
        val liveData = MutableLiveData<Result<List<DogUi>>>()
        liveData.postValue(Result.Success(listOf()))
        Mockito.`when`(downloadRepository.loadDogList()).thenReturn(liveData)
        mainViewModel.dogsLiveData.observeForever {

        }
        mainViewModel.getDogs()
        Assert.assertEquals(liveData.value, mainViewModel.dogsLiveData.value)
    }

    @Test
    fun downloadFile() {
        val url = "url"
        val expected = MutableLiveData<Result<Unit>>()
        expected.postValue(Result.Success(Unit))
        Mockito.`when`(downloadRepository.downloadFile(url))
            .thenReturn(expected)
        mainViewModel.downloadResult.observeForever {

        }

        mainViewModel.downloadFile(url)

        Assert.assertEquals(expected.value, mainViewModel.downloadResult.value)
    }
}