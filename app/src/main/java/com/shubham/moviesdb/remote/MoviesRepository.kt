package com.shubham.moviesdb.remote

import com.shubham.moviesdb.response.MovieDetailsResponse
import com.shubham.moviesdb.response.MovieResponse
import retrofit2.Response
import javax.inject.Inject

class MoviesRepository @Inject constructor(private val api: MoviesApi) {

    suspend fun getMoviesByCategory(category: String): Response<MovieResponse>{
        return api.getMovieResponse(category)
    }
    suspend fun getMoviesById(id: Int): Response<MovieDetailsResponse>{
        return api.getMovieDetails(id)
    }
}