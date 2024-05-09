package net.wandroid.quantummovie.presentation.movie_screen

import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import net.wandroid.quantummovie.R
import net.wandroid.quantummovie.domain.model.Movie
import net.wandroid.quantummovie.presentation.movie_screen.test_util.getString
import org.junit.Rule
import org.junit.Test

class MovieScreenKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun given_query_and_title_and_release_date_when_compose_then_movie_item_is_displayed() {
        //GIVEN
        val movie = Movie(
            title = "title",
            yearOfRelease = "released",
            poster = null,
        )
        val state = MovieUiState(
            movie = movie,
            isLoading = false,
            error = null,
            searchQuery = "query"
        )
        //WHEN
        composeTestRule.setContent {
            MovieScreen(
                uiState = state,
                onEvent = {},
            )
        }
        //THEN
        composeTestRule.onNodeWithText(movie.title).assertExists()
        composeTestRule.onNodeWithText(movie.yearOfRelease).assertExists()
        composeTestRule.onNodeWithText("query").assertExists()
    }


    @Test
    fun given_query_and_no_movie_when_compose_then_n0_result_is_displayed() {
        //GIVEN
        val state = MovieUiState(
            movie = null,
            isLoading = false,
            error = null,
            searchQuery = "query"
        )
        //WHEN
        composeTestRule.setContent {
            MovieScreen(
                uiState = state,
                onEvent = {},
            )
        }
        //THEN
        composeTestRule.onNodeWithText(getString(R.string.movie_screen_no_movie_found)).assertExists()
        composeTestRule.onNodeWithText("query").assertExists()
    }

    @Test
    fun given_no_query_and_no_movie_when_compose_then_search_for_a_movie_is_displayed() {
        //GIVEN
        val state = MovieUiState(
            movie = null,
            isLoading = false,
            error = null,
            searchQuery = ""
        )
        //WHEN
        composeTestRule.setContent {
            MovieScreen(
                uiState = state,
                onEvent = {},
            )
        }

        //THEN
        composeTestRule.onNodeWithText(getString(R.string.movie_screen_search_for_a_movie))
            .assertExists()
    }
}