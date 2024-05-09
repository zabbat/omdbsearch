package net.wandroid.quantummovie.data.remote.dto

import com.squareup.moshi.Json

data class MovieDto(
    @field:Json(name = "Title") val title: String,
    @field:Json(name = "Released") val released: String,
    @field:Json(name = "Poster") val posterUrl: String,
)