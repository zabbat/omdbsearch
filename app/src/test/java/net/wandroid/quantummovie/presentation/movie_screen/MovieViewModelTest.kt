package net.wandroid.quantummovie.presentation.movie_screen

import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import net.wandroid.quantummovie.common.Resource
import net.wandroid.quantummovie.domain.model.Movie
import net.wandroid.quantummovie.domain.use_cases.get_movie.GetMovieUseCase
import net.wandroid.quantummovie.test_util.MainCoroutineRule
import org.junit.Rule
import org.junit.Test

class MovieViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()


    private val getMovieListUseCase: GetMovieUseCase = mockk()
    private val viewModel = MovieViewModel(getMovieListUseCase)

    @Test
    fun `GIVEN a viewmodel WHEN initialized THEN ui state is empty`() {
        //GIVEN
        //WHEN
        val viewModel = MovieViewModel(getMovieListUseCase)
        //THEN
        Truth.assertThat(viewModel.stateFlow.value).isEqualTo(MovieUiState.InitialState)
    }

    @Test
    fun `GIVEN a SearchUpdated event WHEN onEvent is called THEN update search query`() = runTest {
        //GIVEN
        val event = MovieEvent.SearchUpdated("query")
        coEvery { getMovieListUseCase.invoke(any()) } returns flowOf()
        //WHEN
        viewModel.onEvent(event)
        //THEN
        Truth.assertThat(viewModel.stateFlow.value.searchQuery).isEqualTo("query")
    }

    @Test
    fun `GIVEN a blank query WHEN onEvent is called THEN set state to empty`() = runTest {
        //GIVEN
        val event = MovieEvent.SearchUpdated(" ")
        coEvery { getMovieListUseCase.invoke(any()) } returns flowOf()
        //WHEN
        viewModel.onEvent(event)
        //THEN
        Truth.assertThat(viewModel.stateFlow.value).isEqualTo(
            MovieUiState(
                searchQuery = " ",
                isLoading = true,
            )
        )
    }

    @Test
    fun `GIVEN movie resource is Loading WHEN onEvent is called THEN update isLoading state to true`() = runTest {
        //GIVEN
        coEvery { getMovieListUseCase.invoke(any()) } returns flowOf(Resource.Loading())
        //WHEN
        viewModel.getMovie("query")
        //THEN
        Truth.assertThat(viewModel.stateFlow.value.isLoading).isTrue()
    }

    @Test
    fun `GIVEN movie resource is Success WHEN onEvent is called THEN update state to show movie`() = runTest {
        //GIVEN
        val movie = Movie(
            title = "title",
            poster = "poster",
            yearOfRelease = "releaseDate",
        )
        coEvery { getMovieListUseCase.invoke(any()) } returns flowOf(Resource.Success(movie))
        //WHEN
        //viewModel.onEvent(event)
        viewModel.getMovie("query")
        //THEN
        Truth.assertThat(viewModel.stateFlow.value.movie).isEqualTo(movie)
        Truth.assertThat(viewModel.stateFlow.value.error).isNull()
        Truth.assertThat(viewModel.stateFlow.value.isLoading).isFalse()
    }

    @Test
    fun `GIVEN movie resource is Error WHEN onEvent is called THEN update state to show error`() = runTest {
        //GIVEN

        coEvery { getMovieListUseCase.invoke(any()) } returns flowOf(Resource.Error(0, "message"))
        //WHEN
        //viewModel.onEvent(event)
        viewModel.getMovie("query")
        //THEN
        Truth.assertThat(viewModel.stateFlow.value.movie).isNull()
        Truth.assertThat(viewModel.stateFlow.value.error).isEqualTo("message")
        Truth.assertThat(viewModel.stateFlow.value.isLoading).isFalse()
    }
}