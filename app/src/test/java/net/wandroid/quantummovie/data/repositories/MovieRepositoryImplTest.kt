package net.wandroid.quantummovie.data.repositories

import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import net.wandroid.quantummovie.data.remote.OmdbApi
import net.wandroid.quantummovie.data.remote.dto.MovieDto
import net.wandroid.quantummovie.domain.model.Movie
import net.wandroid.quantummovie.domain.repositories.MovieRepository
import org.junit.Test

class MovieRepositoryImplTest {

    private val omdbApi: OmdbApi = mockk()
    private val repository: MovieRepository = MovieRepositoryImpl(omdbApi)

    @Test
    fun `GIVEN a valid query WHEN search THEN return movie`() = runTest {
        // GIVEN
        val movie = Movie(
            poster = "poster",
            title = "title",
            yearOfRelease = "released"
        )
        val movieDto = MovieDto(
            title = "title",
            released = "released",
            posterUrl = "poster"
        )
        coEvery { omdbApi.searchMovie(title = "query") } returns movieDto
        // WHEN
        val result = repository.getMovie("query")
        // THEN
        Truth.assertThat(result).isEqualTo(movie)
    }

}