package com.example.popularmovies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.popularmovies.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class MovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    init {
        fetchPopularMovies()
    }

    val popularMovies: LiveData<List<Movie>>
        get() = movieRepository.movies.map { list ->
            list.filter {
                val cal = Calendar.getInstance()
                it.releaseDate.startsWith(
                    "${cal.get(Calendar.YEAR)}"
                )
            }.sortedByDescending { it.popularity }
        }

    val error: LiveData<String> = movieRepository.error

    private fun fetchPopularMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.fetchMovies()
        }
    }
}