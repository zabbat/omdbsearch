package net.wandroid.quantummovie.domain.use_cases.get_movie

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import net.wandroid.quantummovie.common.Resource
import net.wandroid.quantummovie.domain.model.HttpError
import net.wandroid.quantummovie.domain.model.IOError
import net.wandroid.quantummovie.domain.model.Movie
import net.wandroid.quantummovie.domain.model.UnknownError
import net.wandroid.quantummovie.domain.repositories.MovieRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * Use case for getting a Movie and returns the progress as a [Result]
 */
class GetMovieUseCase @Inject constructor(private val movieRepository: MovieRepository) {
    suspend operator fun invoke(query: String): Flow<Resource<Movie>> = flow {
        emit(Resource.Loading())
        try {
            val movie = movieRepository.getMovie(query)
            emit(Resource.Success(movie))
        } catch (e:Exception){
            when(e){
                is IOException -> emit(Resource.Error(errorId = IOError, message = "Network error"))
                is HttpException -> emit(Resource.Error(errorId = HttpError, message = "Server error"))
                else -> emit(Resource.Error(errorId = UnknownError, message = "An unexpected error occurred"))
            }
        }
    }
}