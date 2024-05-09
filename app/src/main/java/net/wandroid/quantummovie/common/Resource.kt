package net.wandroid.quantummovie.common

/**
 * Class for handling resources that needs to be fetched first
 */
sealed class Resource<T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Error<T>(val errorId: Int, val message: String) : Resource<T>()
    class Loading<T> : Resource<T>()
}