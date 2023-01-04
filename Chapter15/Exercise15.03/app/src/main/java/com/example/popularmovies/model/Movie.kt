package com.example.popularmovies.model

import androidx.room.Entity
import com.squareup.moshi.Json

@Entity(tableName = "movies",  primaryKeys = [("id")])
data class Movie(
    val adult: Boolean = false,
    val backdrop_path: String = "",
    val id: Int = 0,
    @field:Json(name = "original_language")
    val originalLanguage: String = "",
    @field:Json(name = "original_title")
    val originalTitle: String = "",
    val overview: String = "",
    val popularity: Float = 0f,
    @field:Json(name = "poster_path")
    val posterPath: String = "",
    @field:Json(name = "release_date")
    val releaseDate: String = "",
    val title: String = "",
    val video: Boolean = false,
    @field:Json(name = "vote_average")
    val voteAverage: Float = 0f,
    @field:Json(name = "vote_count")
    val voteCount: Int = 0
)