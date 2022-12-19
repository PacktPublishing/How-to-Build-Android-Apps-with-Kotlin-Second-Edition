package com.example.popularmovies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import app.cash.turbine.test
import com.example.popularmovies.model.Movie
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import java.util.*

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class MovieViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun getPopularMovies() {
        val dispatcher = StandardTestDispatcher()

        val movies = listOf(Movie(title = "Movie"))

        val movieRepository: MovieRepository = mock {
            onBlocking { fetchMovies() } doReturn flowOf(movies)
        }

        val movieViewModel = MovieViewModel(movieRepository, dispatcher)

        runTest {
            dispatcher.scheduler.advanceUntilIdle()
            movieViewModel.popularMovies.test {
                assertEquals(movies, awaitItem())
            }
        }
    }

}