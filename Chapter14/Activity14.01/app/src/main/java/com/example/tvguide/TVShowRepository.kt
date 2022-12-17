package com.example.tvguide

import com.example.tvguide.api.TelevisionService
import com.example.tvguide.model.TVShow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class TVShowRepository(private val tvService: TelevisionService) {
    private val apiKey = "your_api_key_here"

    fun fetchTVShows(): Flow<List<TVShow>> {
        return flow {
            emit(tvService.getTVShows(apiKey).results)
        }.flowOn(Dispatchers.IO)
    }
}