package net.wandroid.quantummovie.domain.repositories

import net.wandroid.quantummovie.domain.model.Movie

/**
 * Repository for movies.
 */
interface MovieRepository {
    /**
     * gets a movie by searching for it with query.
     */
    suspend fun getMovie(query: String): Movie
}