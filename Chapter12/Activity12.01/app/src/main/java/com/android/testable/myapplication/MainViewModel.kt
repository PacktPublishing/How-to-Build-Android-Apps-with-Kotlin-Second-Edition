package com.android.testable.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.android.testable.myapplication.repository.DogUi
import com.android.testable.myapplication.repository.DownloadRepository
import com.android.testable.myapplication.repository.Result

class MainViewModel(private val downloadRepository: DownloadRepository) : ViewModel() {

    private val _dogsLiveData: MediatorLiveData<Result<List<DogUi>>> by lazy {
        MediatorLiveData<Result<List<DogUi>>>()
    }
    val dogsLiveData: LiveData<Result<List<DogUi>>> = _dogsLiveData

    private val _downloadResult: MediatorLiveData<Result<Unit>> by lazy {
        MediatorLiveData<Result<Unit>>()
    }
    val downloadResult: LiveData<Result<Unit>> = _downloadResult

    fun getDogs() {
        _dogsLiveData.addSource(downloadRepository.loadDogList()) {
            _dogsLiveData.postValue(it)
        }
    }

    fun downloadFile(url: String) {
        _downloadResult.addSource(downloadRepository.downloadFile(url)) {
            _downloadResult.postValue(it)
        }
    }
}