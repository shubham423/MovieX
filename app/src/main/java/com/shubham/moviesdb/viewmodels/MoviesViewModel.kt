package com.shubham.moviesdb.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shubham.moviesdb.remote.MoviesRepository
import com.shubham.moviesdb.response.CastCreditsResponse
import com.shubham.moviesdb.response.Movie
import com.shubham.moviesdb.response.MovieResponse
import com.shubham.moviesdb.response.SimiliarMoviesResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val repository: MoviesRepository,
) : ViewModel() {

    private val _onPopularMoviesResponse = MutableLiveData<Response<MovieResponse>>()
    val onPopularMoviesResponse: LiveData<Response<MovieResponse>> = _onPopularMoviesResponse

    private val _onTopRatedMoviesResponse = MutableLiveData<Response<MovieResponse>>()
    val onTopRatedMoviesResponse: LiveData<Response<MovieResponse>> = _onTopRatedMoviesResponse

    private val _onNowPlayingMoviesResponse = MutableLiveData<Response<MovieResponse>>()
    val onNowPlayingMoviesResponse: LiveData<Response<MovieResponse>> = _onNowPlayingMoviesResponse

    private val _onMoviesDetailsResponse = MutableLiveData<Response<Movie>>()
    val onMoviesDetailsResponse: LiveData<Response<Movie>> = _onMoviesDetailsResponse

    private val _onCastCreditsResponse = MutableLiveData<Response<CastCreditsResponse>>()
    val onCastCreditsResponse: LiveData<Response<CastCreditsResponse>> = _onCastCreditsResponse

    private val _onSimilarMoviesResponse = MutableLiveData<Response<SimiliarMoviesResponse>>()
    val onSimilarMoviesResponse: LiveData<Response<SimiliarMoviesResponse>> = _onSimilarMoviesResponse

    fun getPopularMovies() {
        viewModelScope.launch {
            // Coroutine that will be canceled when the ViewModel is cleared.
            val response = repository.getMoviesByCategory("popular")
            _onPopularMoviesResponse.postValue(response)
        }
    }

    fun getTopRatedMovies() {
        viewModelScope.launch {
            // Coroutine that will be canceled when the ViewModel is cleared.
            val response = repository.getMoviesByCategory("top_rated")
            _onTopRatedMoviesResponse.postValue(response)
        }
    }

    fun getNowPlayingMovies() {
        viewModelScope.launch {
            // Coroutine that will be canceled when the ViewModel is cleared.
            val response = repository.getMoviesByCategory("now_playing")
            _onNowPlayingMoviesResponse.postValue(response)
        }
    }

    fun getMovieById(id : Int) {
        viewModelScope.launch {
            // Coroutine that will be canceled when the ViewModel is cleared.
            val response = repository.getMoviesById(id)
            _onMoviesDetailsResponse.postValue(response)
        }
    }

    fun getCastCredits(id : Int) {
        viewModelScope.launch {
            // Coroutine that will be canceled when the ViewModel is cleared.
            val response = repository.getMoviesCredits(id)
            _onCastCreditsResponse.postValue(response)
        }
    }

    fun getSimilarMovies(id : Int) {
        viewModelScope.launch {
            // Coroutine that will be canceled when the ViewModel is cleared.
            val response = repository.getSimilarMovies(id)
            _onSimilarMoviesResponse.postValue(response)
        }
    }




}