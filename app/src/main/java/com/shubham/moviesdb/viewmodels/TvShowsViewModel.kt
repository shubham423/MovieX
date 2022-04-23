package com.shubham.moviesdb.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shubham.moviesdb.remote.MoviesRepository
import com.shubham.moviesdb.remote.TvShowsRepository
import com.shubham.moviesdb.response.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class TvShowsViewModel @Inject constructor(
    private val repository: TvShowsRepository,
) : ViewModel() {

    private val _onPopularTvShowsResponse = MutableLiveData<Response<TvShowResponse>>()
    val onPopularTvShowsResponse: LiveData<Response<TvShowResponse>> = _onPopularTvShowsResponse

    private val _onTopRatedTvShowsResponse = MutableLiveData<Response<TvShowResponse>>()
    val onTopRatedTvShowsResponse: LiveData<Response<TvShowResponse>> = _onTopRatedTvShowsResponse


    private val _onNowPlayingTvShowsResponse = MutableLiveData<Response<TvShowResponse>>()
    val onNowPlayingTvShowsResponse: LiveData<Response<TvShowResponse>> = _onNowPlayingTvShowsResponse

    private val _onTvShowDetailsResponse = MutableLiveData<Response<TvShow>>()
    val onTvShowDetailsResponse: LiveData<Response<TvShow>> = _onTvShowDetailsResponse

    private val _onCastCreditsResponse = MutableLiveData<Response<CastCreditsResponse>>()
    val onCastCreditsResponse: LiveData<Response<CastCreditsResponse>> = _onCastCreditsResponse

    fun getPopularTvShows() {
        viewModelScope.launch {
            val response = repository.getTvShowsByCategory("popular")
            _onPopularTvShowsResponse.postValue(response)
        }
    }

    fun getTopRatedTvShows() {
        viewModelScope.launch {
            val response = repository.getTvShowsByCategory("top_rated")
            _onTopRatedTvShowsResponse.postValue(response)
        }
    }

    fun getNowPlayingTvShows() {
        viewModelScope.launch {
            val response = repository.getTvShowsByCategory("on_the_air")
            _onNowPlayingTvShowsResponse.postValue(response)
        }
    }


    fun getShowById(id : Int) {
        viewModelScope.launch {
            val response = repository.getTvShowDetail(id)
            _onTvShowDetailsResponse.postValue(response)
        }
    }
}