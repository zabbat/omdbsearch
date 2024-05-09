package net.wandroid.quantummovie.data.remote

import net.wandroid.quantummovie.data.remote.dto.MovieDto
import retrofit2.http.GET
import retrofit2.http.Query
interface OmdbApi {
    @GET("/")
    suspend fun searchMovie(
        @Query("apikey") apikey: String = DEBUG_KEY,
        @Query("t") title: String
    ): MovieDto
    companion object{
        const val BASE_URL = "https://www.omdbapi.com"
        const val DEBUG_KEY = "d315ac8d"
    }
}