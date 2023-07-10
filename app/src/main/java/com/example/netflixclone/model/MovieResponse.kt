package com.example.netflixclone.model


import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("results")
    val results: List<MoviesResult>
)



