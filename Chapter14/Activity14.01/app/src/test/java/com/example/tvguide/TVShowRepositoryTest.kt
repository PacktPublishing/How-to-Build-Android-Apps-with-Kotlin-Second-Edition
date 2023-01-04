package com.example.tvguide

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.example.tvguide.api.TelevisionService
import com.example.tvguide.model.TVResponse
import com.example.tvguide.model.TVShow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.mock

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class TVShowRepositoryTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun fetchTVShows() {
        val tvShows = listOf(TVShow(id = 3), TVShow(id = 4))
        val remoteResponse = TVResponse(1, tvShows)

        val tvService: TelevisionService = mock {
            onBlocking { getTVShows(anyString()) } doReturn remoteResponse
        }

        val tvShowRepository = TVShowRepository(tvService)

        runTest {
            tvShowRepository.fetchTVShows().test {
                assertEquals(tvShows, awaitItem())
                awaitComplete()
            }
        }
    }

    @Test
    fun fetchTVShowsError() {
        val exception = "TV Show Exception"

        val tvService: TelevisionService = mock {
            onBlocking { getTVShows(anyString()) } doThrow RuntimeException(exception)
        }

        val tvShowRepository = TVShowRepository(tvService)

        runTest {
            tvShowRepository.fetchTVShows().test {
                assertEquals(exception, awaitError().message)
            }
        }
    }
}