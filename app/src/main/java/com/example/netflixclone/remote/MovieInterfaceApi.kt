package com.example.netflixclone.remote

import com.example.netflixclone.model.MovieDetails
import com.example.netflixclone.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MovieInterfaceApi {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") api_key: String,
        @Query("page") page: Int
    ): Response<MovieResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") id: Int,
        @Query("api_key") api_key: String
    ): Response<MovieDetails>


    @GET("search/movie")
    suspend fun SearchMovies(
        @Query("api_key") api_key: String,
        @Query("query") Query: String
    ): Response<MovieResponse>
}