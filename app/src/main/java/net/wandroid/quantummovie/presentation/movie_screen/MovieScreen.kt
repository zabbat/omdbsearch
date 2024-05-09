package net.wandroid.quantummovie.presentation.movie_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.wandroid.quantummovie.R
import net.wandroid.quantummovie.domain.model.Movie

@Composable
fun MovieScreen(
    modifier: Modifier = Modifier,
    uiState: MovieUiState,
    onEvent: (MovieEvent) -> Unit,
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = uiState.searchQuery,
            label = { Text(stringResource(R.string.movie_screen_search)) },
            trailingIcon = {
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        strokeWidth = 4.dp
                    )
                }
            },
            onValueChange = { text ->
                onEvent(
                    MovieEvent.SearchUpdated(text)
                )
            }
        )
        Spacer(modifier = Modifier.size(16.dp))

        if (uiState.error != null) {
            Text(
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                text = uiState.error,
                style = MaterialTheme.typography.bodyLarge
            )
        } else {
            if (uiState.movie != null) {
                MovieItem(
                    title = uiState.movie.title,
                    yearOfRelease = uiState.movie.yearOfRelease,
                    posterUrl = uiState.movie.poster,
                    onClick = {
                        onEvent(
                            MovieEvent.MovieClicked(uiState.movie)
                        )
                    }
                )
            } else {
                if (!uiState.isLoading) {
                    val text = if (uiState.searchQuery.isEmpty()) {
                        stringResource(R.string.movie_screen_search_for_a_movie)
                    } else {
                        stringResource(R.string.movie_screen_no_movie_found)
                    }
                    Text(
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        text = text,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewMovieListScreen() {
    val hasMovieState = MovieUiState(
        movie = Movie(
            poster = null,
            title = "the movie title if it was very long",
            yearOfRelease = "25 May 1977"
        ),
        searchQuery = "abc",
        error = null,
    )
    val emptyState = MovieUiState.InitialState
    val noMovieState = MovieUiState(
        movie = null,
        searchQuery = "abc",
        error = null,
    )

    val errorState = MovieUiState(
        movie = null,
        searchQuery = "abc",
        error = "Unexpected error",
    )

    MovieScreen(
        uiState = hasMovieState,
        onEvent = {}
    )
}