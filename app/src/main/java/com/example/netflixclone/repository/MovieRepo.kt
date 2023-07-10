package com.example.netflixclone.repository

import com.example.netflixclone.model.MovieDetails
import com.example.netflixclone.model.MovieResponse
import com.example.netflixclone.remote.MovieInterfaceApi
import com.example.netflixclone.utils.Constants.API_KEY
import retrofit2.Response
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepo @Inject constructor(private val MovieInterfaceApi: MovieInterfaceApi) {

    suspend fun getMovieResponse(page: Int): Response<MovieResponse> {
        return MovieInterfaceApi.getPopularMovies(API_KEY, page)
    }

    suspend fun getMovieDetails(movieId: Int): Response<MovieDetails> {
        return MovieInterfaceApi.getMovieDetails(movieId, API_KEY)
    }

    suspend fun searchMovies(query: String): Response<MovieResponse> {
        return MovieInterfaceApi.SearchMovies(API_KEY, query)
    }

}