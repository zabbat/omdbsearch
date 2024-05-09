package net.wandroid.quantummovie.presentation.movie_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import net.wandroid.quantummovie.common.Resource
import net.wandroid.quantummovie.domain.use_cases.get_movie.GetMovieUseCase
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getMovieUseCase: GetMovieUseCase,
) : ViewModel() {
    private val _stateFlow = MutableStateFlow(MovieUiState.InitialState)
    val stateFlow = _stateFlow.asStateFlow()

    private var job: Job? = null

    suspend fun getMovie(query: String) {
        // don't allow blank/empty searches
        if (query.isBlank()) {
            _stateFlow.update {
                MovieUiState.InitialState.copy(searchQuery = query)
            }
            return
        }

        getMovieUseCase(query).collect { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _stateFlow.update {
                        it.copy(
                            isLoading = true,
                        )
                    }
                }

                is Resource.Success -> {
                    _stateFlow.update {
                        it.copy(
                            movie = resource.data,
                            isLoading = false,
                            error = null,
                        )
                    }
                }

                is Resource.Error -> {
                    // Normally you would not use a text from domain in presentation layer.
                    // An improvement here would be to use the errorId and look it up against the error codes,
                    // and then use an appropriate string resource. For this small project however we will assume
                    // that the error message from domain is ok.
                    _stateFlow.update {
                        it.copy(
                            movie = null,
                            isLoading = false,
                            error = resource.message,
                        )
                    }
                }
            }
        }
    }

    fun onEvent(event: MovieEvent) {
        when (event) {
            is MovieEvent.SearchUpdated -> {
                _stateFlow.update {
                    it.copy(
                        searchQuery = event.query,
                        isLoading = true,
                    )
                }
                // delay search with SEARCH_DELAY, and cancel the job if the search query changes.
                // This to not spam the server with requests as the user types
                if (job != null) {
                    job?.cancel()
                }
                job = viewModelScope.launch {
                    delay(SEARCH_DELAY)
                    getMovie(event.query)
                }
            }

            is MovieEvent.MovieClicked -> {
                // Does nothing for now
                Log.d("MovieListViewModel", "Movie clicked: ${event.movie.title}")
            }
        }
    }

    companion object {
        private const val SEARCH_DELAY = 1000L
    }
}