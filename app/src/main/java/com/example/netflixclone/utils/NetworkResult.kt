package com.example.netflixclone.utils


sealed class NetworkResult<T>(  //generics can recieve multiple type of data
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : NetworkResult<T>(data)
    class Error<T>(message: String?, data: T? = null) : NetworkResult<T>(data, message)
    class Loading<T>: NetworkResult<T>()
}