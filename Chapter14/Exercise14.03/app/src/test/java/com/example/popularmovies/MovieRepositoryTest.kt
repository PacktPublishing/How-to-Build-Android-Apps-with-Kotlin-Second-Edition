package com.example.popularmovies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.example.popularmovies.api.MovieService
import com.example.popularmovies.model.Movie
import com.example.popularmovies.model.PopularMoviesResponse
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
class MovieRepositoryTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun fetchMovies() {
        val movies = listOf(Movie(id = 3), Movie(id = 4))
        val response = PopularMoviesResponse(1, movies)

        val movieService: MovieService = mock {
            onBlocking { getPopularMovies(anyString()) } doReturn response
        }

        val movieRepository = MovieRepository(movieService)

        runTest {
            movieRepository.fetchMovies().test {
                assertEquals(movies, awaitItem())
                awaitComplete()
            }
        }
    }

    @Test
    fun fetchMoviesError() {
        val exception = "Test Exception"

        val movieService: MovieService = mock {
            onBlocking { getPopularMovies(anyString()) } doThrow RuntimeException(exception)
        }

        val movieRepository = MovieRepository(movieService)

        runTest {
            movieRepository.fetchMovies().test {
                assertEquals(exception, awaitError().message)
            }
        }
    }
}