package net.wandroid.quantummovie.presentation.movie_screen

import net.wandroid.quantummovie.domain.model.Movie

/**
 * UI state for the movie screen
 */
data class MovieUiState(
    /**
     * List of movies to display. Null means there is no movie to display.
     */
    val movie: Movie? = null,
    /**
     * True if movies are loading, false otherwise.
     */
    val isLoading: Boolean = false,
    /**
     * Error message to display, or null if there is no error.
     */
    val error: String? = null,
    /**
     * Query used to search for movies.
     */
    val searchQuery: String = "",
) {
    companion object {
        val InitialState = MovieUiState()
    }
}