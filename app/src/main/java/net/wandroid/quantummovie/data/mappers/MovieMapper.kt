package net.wandroid.quantummovie.data.mappers

import net.wandroid.quantummovie.data.remote.dto.MovieDto
import net.wandroid.quantummovie.domain.model.Movie

fun MovieDto.toMovie(): Movie = Movie(
    poster = posterUrl,
    title = title,
    yearOfRelease = released,
)