package net.wandroid.quantummovie.domain.model

/**
 * Describes a movie.
 * @property poster The url to the poster of the movie. Null if no poster is available.
 * @property title The title of the movie.
 * @property yearOfRelease The year of release of the movie.
 */
data class Movie(
    val poster: String?,
    val title: String,
    val yearOfRelease: String,
)
