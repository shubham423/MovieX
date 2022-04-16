package com.shubham.moviesdb.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.shubham.moviesdb.local.database.MovieEntity
import com.shubham.moviesdb.remote.MoviesRepository
import com.shubham.moviesdb.response.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
    val onSimilarMoviesResponse: LiveData<Response<SimiliarMoviesResponse>> =
        _onSimilarMoviesResponse

    private val _onCastDetailsResponse = MutableLiveData<Response<ActorResponse>>()
    val onCastDetailsResponse: LiveData<Response<ActorResponse>> = _onCastDetailsResponse

    private val _onSearchMoviesResponse = MutableLiveData<Response<MovieResponse>>()
    val onSearchMoviesResponse: LiveData<Response<MovieResponse>> = _onSearchMoviesResponse

    private val _onAllMoviesResponse = MutableLiveData<List<MovieEntity>>()
    val onAllMoviesResponse: LiveData<List<MovieEntity>> = _onAllMoviesResponse

    private val _onIsMovieExist = MutableLiveData<Boolean>()
    val onIsMovieExist: LiveData<Boolean> = _onIsMovieExist

    fun getPopularMovies() {
        viewModelScope.launch {
            val response = repository.getMoviesByCategory("popular")
            _onPopularMoviesResponse.postValue(response)
        }
    }

    fun getTopRatedMovies() {
        viewModelScope.launch {
            val response = repository.getMoviesByCategory("top_rated")
            _onTopRatedMoviesResponse.postValue(response)
        }
    }

    fun getNowPlayingMovies() {
        viewModelScope.launch {
            val response = repository.getMoviesByCategory("now_playing")
            _onNowPlayingMoviesResponse.postValue(response)
        }
    }

    fun getMovieById(id: Int) {
        viewModelScope.launch {
            val response = repository.getMoviesById(id)
            _onMoviesDetailsResponse.postValue(response)
        }
    }

    fun getCastCredits(id: Int) {
        viewModelScope.launch {
            val response = repository.getMoviesCredits(id)
            _onCastCreditsResponse.postValue(response)
        }
    }

    fun getSimilarMovies(id: Int) {
        viewModelScope.launch {
            val response = repository.getSimilarMovies(id)
            _onSimilarMoviesResponse.postValue(response)
        }
    }

    fun getActorDetails(castId: Int) {
        viewModelScope.launch {
            val response = repository.getActor(castId)
            _onCastDetailsResponse.postValue(response)
        }
    }

    fun searchMovie(query: String) {
        viewModelScope.launch {
            val response = repository.searchMovie(query)
            _onSearchMoviesResponse.postValue(response)
        }
    }

    fun addFavoriteMovie(movie: MovieEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.addFavoriteMovie(movie)
    }

    fun deleteFavMovie(movie: MovieEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.removeFavoriteMovie(movie)
    }

    fun getAllMovies() {
        viewModelScope.launch {
            val response=repository.getFavoriteMovies()
            _onAllMoviesResponse.postValue(response)
        }
    }

    fun isMovieExist(id: Int){
        viewModelScope.launch {
            val response=repository.isMovieExist(id)
            _onIsMovieExist.postValue(response)
        }
    }





}
