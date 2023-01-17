package com.android.testable.myapplication

import com.android.testable.myapplication.repository.PostRepository
import com.android.testable.myapplication.repository.RepositoryModule
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class]
)
class TestRepositoryModule {

    @Singleton
    @Provides
    fun providePostRepository(): PostRepository {
        return DummyRepository()
    }
}