package net.wandroid.quantummovie.presentation.movie_screen

import net.wandroid.quantummovie.domain.model.Movie

/**
 * Events on the Movie Screen
 */
sealed class MovieEvent {
    /**
     * When search text changes
     */
    data class SearchUpdated(val query: String) : MovieEvent()

    /**
     * When a movie is clicked
     */
    data class MovieClicked(val movie: Movie) : MovieEvent()
}
