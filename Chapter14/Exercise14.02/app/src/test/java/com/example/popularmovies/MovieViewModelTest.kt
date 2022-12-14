package com.example.popularmovies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.popularmovies.model.Movie
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class MovieViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var movieRepository: MovieRepository

    @Test
    fun getPopularMovies() {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, -1)
        val releaseDate = "${calendar.get(Calendar.YEAR)}-${calendar.get(Calendar.MONTH) + 1}"

        val movieLiveData = MutableLiveData<List<Movie>>()
        val popularMovies = listOf(
            Movie(
                title = "Title",
                releaseDate = releaseDate
            )
        )
        movieLiveData.postValue(popularMovies)

        Mockito.`when`(movieRepository.movies)
            .thenReturn(movieLiveData)
        val movieViewModel = MovieViewModel(movieRepository)

        assertEquals(
            movieLiveData.value,
            movieViewModel.popularMovies.getOrAwaitValue()
        )
    }

}