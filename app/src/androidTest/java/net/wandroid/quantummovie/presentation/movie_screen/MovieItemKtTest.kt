package net.wandroid.quantummovie.presentation.movie_screen

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import net.wandroid.quantummovie.R
import net.wandroid.quantummovie.presentation.movie_screen.test_util.getString
import org.junit.Rule
import org.junit.Test

class MovieItemKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun given_title_and_release_date_when_compose_then_movie_item_is_displayed() {
        //GIVEN
        val title = "title"
        val releaseDate = "released"
        //WHEN
        composeTestRule.setContent {
            MovieItem(
                title = title,
                yearOfRelease = releaseDate,
                posterUrl = null,
                onClick = {}
            )
        }
        //THEN
        composeTestRule.onNodeWithText(title).assertExists()
        composeTestRule.onNodeWithText(releaseDate).assertExists()
        composeTestRule.onNodeWithText(getString(R.string.movie_item_details)).assertExists()
    }
}