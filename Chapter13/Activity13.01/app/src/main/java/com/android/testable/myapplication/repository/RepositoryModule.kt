package com.android.testable.myapplication.repository

import com.android.testable.myapplication.api.PostService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun providePostRepository(postService: PostService): PostRepository {
        return PostRepositoryImpl(postService)
    }
}