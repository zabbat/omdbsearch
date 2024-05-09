package net.wandroid.quantummovie.data.mappers

import com.google.common.truth.Truth
import net.wandroid.quantummovie.data.remote.dto.MovieDto
import net.wandroid.quantummovie.domain.model.Movie
import org.junit.Assert.*
import org.junit.Test

class MovieMapperKtTest {
    @Test
    fun `GIVEN a MovieDto WHEN mapToMovie THEN return a Movie`() {
        //GIVEN
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
        //WHEN
        val result = movieDto.toMovie()
        //THEN
        Truth.assertThat(result).isEqualTo(movie)
    }
}