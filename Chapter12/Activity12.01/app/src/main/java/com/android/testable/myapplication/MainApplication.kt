package com.android.testable.myapplication

import android.app.Application
import androidx.room.Room
import com.android.testable.myapplication.api.DownloadService
import com.android.testable.myapplication.repository.DogMapper
import com.android.testable.myapplication.repository.DownloadRepository
import com.android.testable.myapplication.repository.DownloadRepositoryImpl
import com.android.testable.myapplication.storage.filesystem.FileToUriMapper
import com.android.testable.myapplication.storage.filesystem.ProviderFileHandler
import com.android.testable.myapplication.storage.preference.DownloadPreferencesWrapper
import com.android.testable.myapplication.storage.room.DogDatabase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors

class MainApplication : Application() {

    lateinit var downloadRepository: DownloadRepository
    lateinit var preferencesWrapper: DownloadPreferencesWrapper

    override fun onCreate() {
        super.onCreate()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val downloadService = retrofit.create<DownloadService>(DownloadService::class.java)
        val database =
            Room.databaseBuilder(applicationContext, DogDatabase::class.java, "dog-db")
                .build()
        preferencesWrapper = DownloadPreferencesWrapper(
            getSharedPreferences("my_shared_preferences", MODE_PRIVATE)
        )
        downloadRepository = DownloadRepositoryImpl(
            preferencesWrapper,
            ProviderFileHandler(
                this,
                FileToUriMapper()
            ),
            downloadService,
            database.dogDao(),
            DogMapper(),
            Executors.newSingleThreadExecutor()
        )
    }
}