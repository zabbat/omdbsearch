package net.wandroid.quantummovie.data.repositories

import net.wandroid.quantummovie.data.mappers.toMovie
import net.wandroid.quantummovie.data.remote.OmdbApi
import net.wandroid.quantummovie.domain.model.Movie
import net.wandroid.quantummovie.domain.repositories.MovieRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Returns movies from Omdb
 */
@Singleton
class MovieRepositoryImpl @Inject constructor(private val omdbApi: OmdbApi) : MovieRepository {
    override suspend fun getMovie(query: String): Movie {
        return omdbApi.searchMovie(title = query).toMovie()
    }
}