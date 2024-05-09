package net.wandroid.quantummovie.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint
import net.wandroid.quantummovie.domain.model.Movie
import net.wandroid.quantummovie.presentation.movie_screen.MovieEvent
import net.wandroid.quantummovie.presentation.movie_screen.MovieScreen
import net.wandroid.quantummovie.presentation.movie_screen.MovieUiState
import net.wandroid.quantummovie.presentation.movie_screen.MovieViewModel
import net.wandroid.quantummovie.presentation.theme.QuantumMovieTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val viewModel: MovieViewModel by viewModels()
        setContent {
            val uiState by viewModel.stateFlow.collectAsState()
            MainScreen(
                uiState = uiState,
                onEvent = viewModel::onEvent
            )
        }
    }
}

@Composable
fun MainScreen(
    uiState: MovieUiState,
    onEvent: (MovieEvent) -> Unit,
) {
    QuantumMovieTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MovieScreen(
                modifier = Modifier.padding(innerPadding),
                uiState = uiState,
                onEvent = onEvent,
            )
        }
    }
}

@Composable
@Preview
fun PreviewMainScreen() {
    val uiState = MovieUiState(
        movie = Movie(
            poster = null,
            title = "the movie title",
            yearOfRelease = "25 May 1977"
        ),
        searchQuery = "abc",
        error = null,
    )
    MainScreen(
        uiState = uiState,
        {},
    )
}