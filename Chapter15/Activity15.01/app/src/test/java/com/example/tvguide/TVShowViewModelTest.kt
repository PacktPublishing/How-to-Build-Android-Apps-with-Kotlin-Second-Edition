package com.example.tvguide

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.example.tvguide.model.TVShow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class TVShowViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun getTVShows() {
        val dispatcher = StandardTestDispatcher()
        val tVShows = listOf(TVShow(id = 5), TVShow(id = 6))

        val tvShowRepository: TVShowRepository = mock {
            onBlocking { fetchTVShows() } doReturn flowOf(tVShows)
        }

        val tvShowViewModel = TVShowViewModel(tvShowRepository, dispatcher)

        runTest {
            dispatcher.scheduler.advanceUntilIdle()
            tvShowViewModel.tvShows.test {
                assertEquals(tVShows, awaitItem())
            }
        }
    }

}