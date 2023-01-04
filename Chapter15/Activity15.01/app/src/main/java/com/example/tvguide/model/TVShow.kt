package com.example.tvguide.model

import androidx.room.Entity
import com.squareup.moshi.Json

@Entity(tableName = "tvshows",  primaryKeys = [("id")])
data class TVShow(
    @field:Json(name = "backdrop_path")
    val backdropPath: String? = "",
    @field:Json(name = "first_air_date")
    val firstAirDate: String = "",
    val id: Int = 0,
    val name: String = "",
    @field:Json(name = "original_language")
    val originalLanguage: String = "",
    @field:Json(name = "original_name")
    val originalName: String = "",
    val overview: String = "",
    val popularity: Float = 0f,
    @field:Json(name = "poster_path")
    val posterPath: String? = "",
    @field:Json(name = "vote_average")
    val voteAverage: Float = 0f,
    @field:Json(name = "vote_count")
    val voteCount: Int = 0
)
