package com.example.popularmovies

import android.util.Log
import com.example.popularmovies.api.MovieService
import com.example.popularmovies.database.MovieDao
import com.example.popularmovies.database.MovieDatabase
import com.example.popularmovies.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MovieRepository(private val movieService: MovieService, private val movieDatabase: MovieDatabase) {
    private val apiKey = "your_api_key_here"

    fun fetchMovies(): Flow<List<Movie>> {
        return flow {
            val movieDao: MovieDao = movieDatabase.movieDao()
            val savedMovies = movieDao.getMovies()
            if(savedMovies.isEmpty()) {
                val movies = movieService.getPopularMovies(apiKey).results
                movieDao.addMovies(movies)
                emit(movies)
            } else {
                emit(savedMovies)
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun fetchMoviesFromNetwork() {
        val movieDao: MovieDao = movieDatabase.movieDao()
        try {
            val popularMovies = movieService.getPopularMovies(apiKey)
            val moviesFetched = popularMovies.results
            movieDao.addMovies(moviesFetched)
        } catch (exception: Exception) {
            Log.d("MovieRepository", "An error occurred: ${exception.message}")
        }
    }
}