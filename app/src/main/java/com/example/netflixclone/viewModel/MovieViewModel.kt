package com.example.netflixclone.viewModel
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.netflixclone.model.MovieDetails
import com.example.netflixclone.model.MovieResponse
import com.example.netflixclone.repository.MovieRepo
import com.example.netflixclone.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    application: Application,
    private val movieRepo: MovieRepo
) :
    AndroidViewModel(application) {

    private val _movieResponse: MutableLiveData<NetworkResult<MovieResponse>> = MutableLiveData()
    var movieResponse: LiveData<NetworkResult<MovieResponse>> = _movieResponse

    private val _movieDetailsResponse: MutableLiveData<NetworkResult<MovieDetails>> = MutableLiveData()
    var movieDetailsResponse: LiveData<NetworkResult<MovieDetails>> = _movieDetailsResponse

    private val _searchMovieResponse: MutableLiveData<NetworkResult<MovieResponse>> = MutableLiveData()
    var searchMovieResponse: LiveData<NetworkResult<MovieResponse>> = _searchMovieResponse


    fun fetchPopularMovies(page: Int) {
        try {
            viewModelScope.launch {
                val response = handleMoviesResponse(movieRepo.getMovieResponse(page))
                _movieResponse.value = response
            }


        } catch (e: Exception) {
            Log.e("API_Response", "Error fetching popular movies: ${e.message}")
        }

    }

    fun getFetchMovieDetails(movieId: Int) {
        try {
            viewModelScope.launch {
                val response = handleMoviesDetailsResponse(movieRepo.getMovieDetails(movieId))
                _movieDetailsResponse.value = response
            }
        }
        catch (e:Exception) {
            Log.e("API_Details_Response", "Error fetching popular movie details: ${e.message}")
        }

    }

    fun getSearchMovies(query: String) {
        try {
            viewModelScope.launch {
                val response = handleMoviesResponse(movieRepo.searchMovies(query))
                _searchMovieResponse.value = response
            }
        }
        catch (e:Exception) {
            Log.e("API_Search_Response", "Error fetching popular movie details: ${e.message}")

        }
    }


    //Handling API responses
    private fun handleMoviesResponse(response: Response<MovieResponse>): NetworkResult<MovieResponse> {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }

            response.code() == 402 -> {
                return NetworkResult.Error("API key Limited")
            }

            response.body()!!.results.isNullOrEmpty() -> {
                return NetworkResult.Error("Recipes not found")
            }

            response.isSuccessful -> {
                val MovieResponse = response.body()
                Log.d("Response", response.body().toString())
                return NetworkResult.Success(MovieResponse!!)
            }

            else -> {
                return NetworkResult.Error(response.message())
            }

        }
    }

    private fun handleMoviesDetailsResponse(response: Response<MovieDetails>): NetworkResult<MovieDetails> {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }

            response.code() == 402 -> {
                return NetworkResult.Error("API key Limited")
            }

            response.isSuccessful -> {
                val MovieDetailsResponse = response.body()
                Log.d("Response", response.body().toString())
                return NetworkResult.Success(MovieDetailsResponse!!)
            }

            else -> {
                return NetworkResult.Error(response.message())
            }

        }
    }


    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork ?: return false

        val capabilities =
            connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

}
