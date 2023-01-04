package com.example.tvguide

import android.util.Log
import com.example.tvguide.api.TelevisionService
import com.example.tvguide.database.TVDao
import com.example.tvguide.database.TVDatabase
import com.example.tvguide.model.TVShow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class TVShowRepository(
    private val tvService: TelevisionService,
    private val tvDatabase: TVDatabase
) {
    private val apiKey = "your_api_key_here"

    fun fetchTVShows(): Flow<List<TVShow>> {
        return flow {
            val tvDao: TVDao = tvDatabase.tvDao()
            val savedTVShows = tvDao.getTVShows()
            if (savedTVShows.isEmpty()) {
                val tvShows = tvService.getTVShows(apiKey).results
                tvDao.addTVShows(tvShows)
                emit(tvShows)
            } else {
                emit(savedTVShows)
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun fetchTVShowsFromNetwork() {
        val tvDao: TVDao = tvDatabase.tvDao()
        try {
            val tvShows = tvService.getTVShows(apiKey)
            val shows = tvShows.results
            tvDao.addTVShows(shows)
        } catch (exception: Exception) {
            Log.d("TVShowRepository", "An error occurred: ${exception.message}")
        }
    }
}