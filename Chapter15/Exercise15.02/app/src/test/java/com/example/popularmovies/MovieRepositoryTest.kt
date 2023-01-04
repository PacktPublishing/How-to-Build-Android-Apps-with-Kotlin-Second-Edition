package com.example.popularmovies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.example.popularmovies.api.MovieService
import com.example.popularmovies.database.MovieDao
import com.example.popularmovies.database.MovieDatabase
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
import org.mockito.kotlin.mock

@OptIn(ExperimentalCoroutinesApi::class)

@RunWith(MockitoJUnitRunner::class)
class MovieRepositoryTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun fetchCachedMovies() {
        val cachedMovies = listOf(Movie(id = 1), Movie(id = 2))
        val dao = object : MovieDao {
            override fun addMovies(movies: List<Movie>) {

            }

            override fun getMovies(): List<Movie> {
                return cachedMovies
            }

        }

        val movieDatabase: MovieDatabase = mock {
            on { movieDao() } doReturn dao
        }

        val movieService: MovieService = mock()

        val movieRepository = MovieRepository(movieService, movieDatabase)

        runTest {
            movieRepository.fetchMovies().test {
                assertEquals(cachedMovies, awaitItem())
                awaitComplete()
            }
        }
    }

    @Test
    fun fetchRemoteMovies() {
        val remoteMovies = listOf(Movie(id = 3), Movie(id = 4))
        val remoteResponse = PopularMoviesResponse(1, remoteMovies)

        val emptyDao = object : MovieDao {
            override fun addMovies(movies: List<Movie>) {

            }

            override fun getMovies(): List<Movie> = emptyList()

        }

        val movieDatabase: MovieDatabase = mock {
            on { movieDao() } doReturn emptyDao
        }

        val movieService: MovieService = mock {
            onBlocking { getPopularMovies(anyString()) } doReturn remoteResponse
        }

        val movieRepository = MovieRepository(movieService, movieDatabase)

        runTest {
            movieRepository.fetchMovies().test {
                assertEquals(remoteMovies, awaitItem())
                awaitComplete()
            }
        }
    }
}