package net.wandroid.quantummovie.domain.use_cases.get_movie

import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import net.wandroid.quantummovie.common.Resource
import net.wandroid.quantummovie.domain.model.HttpError
import net.wandroid.quantummovie.domain.model.IOError
import net.wandroid.quantummovie.domain.model.Movie
import net.wandroid.quantummovie.domain.model.UnknownError
import net.wandroid.quantummovie.domain.repositories.MovieRepository
import org.junit.Test
import retrofit2.HttpException
import java.io.IOException

class GetMovieUseCaseTest {

    private val repository: MovieRepository = mockk()
    private val getMovieUseCase = GetMovieUseCase(repository)

    @Test
    fun `GIVEN a query WHEN invoke THEN emit Loading`() = runTest {
        //GIVEN
        val movie = mockk<Movie>()
        coEvery { repository.getMovie(any()) } returns movie
        //WHEN
        val result = getMovieUseCase.invoke("query").first()
        //THEN
        Truth.assertThat(result).isInstanceOf(Resource.Loading::class.java)
    }

    @Test
    fun `GIVEN a valid query WHEN invoke THEN emit Success`() = runTest {
        //GIVEN
        val movie = mockk<Movie>()
        coEvery { repository.getMovie(any()) } returns movie
        //WHEN
        val result = getMovieUseCase.invoke("query").last()
        //THEN
        Truth.assertThat((result as Resource.Success).data).isEqualTo(movie)
    }

    @Test
    fun `GIVEN an HttpException WHEN invoke THEN emit Error with error code`() = runTest {
        //GIVEN
        val movie = mockk<Movie>()
        coEvery { repository.getMovie(any()) } throws mockk<HttpException>()
        //WHEN
        val result = getMovieUseCase.invoke("query").last()
        //THEN
        Truth.assertThat((result as Resource.Error).errorId).isEqualTo(HttpError)
    }

    @Test
    fun `GIVEN an IOException WHEN invoke THEN emit Error with error code`() = runTest {
        //GIVEN
        val movie = mockk<Movie>()
        coEvery { repository.getMovie(any()) } throws IOException()
        //WHEN
        val result = getMovieUseCase.invoke("query").last()
        //THEN
        Truth.assertThat((result as Resource.Error).errorId).isEqualTo(IOError)
    }

    @Test
    fun `GIVEN an unknown Exception WHEN invoke THEN emit Error with error code`() = runTest {
        //GIVEN
        val movie = mockk<Movie>()
        coEvery { repository.getMovie(any()) } throws Exception()
        //WHEN
        val result = getMovieUseCase.invoke("query").last()
        //THEN
        Truth.assertThat((result as Resource.Error).errorId).isEqualTo(UnknownError)
    }
}