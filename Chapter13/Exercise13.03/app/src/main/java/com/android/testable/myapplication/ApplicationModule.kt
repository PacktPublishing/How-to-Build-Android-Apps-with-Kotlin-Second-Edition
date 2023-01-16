package com.android.testable.myapplication

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.*

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    fun provideRandom(): Random = Random()

    @Provides
    fun provideNumberRepository(random: Random): NumberRepository = NumberRepositoryImpl(random)
}