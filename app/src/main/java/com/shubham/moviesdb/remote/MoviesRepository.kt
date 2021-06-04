package com.shubham.moviesdb.remote

import com.shubham.moviesdb.response.CastCreditsResponse
import com.shubham.moviesdb.response.Movie
import com.shubham.moviesdb.response.MovieResponse
import com.shubham.moviesdb.response.SimiliarMoviesResponse
import retrofit2.Response
import javax.inject.Inject

class MoviesRepository @Inject constructor(private val api: MoviesApi) {

    suspend fun getMoviesByCategory(category: String): Response<MovieResponse>{
        return api.getMovieResponse(category)
    }
    suspend fun getMoviesById(id: Int): Response<Movie>{
        return api.getMovieDetails(id)
    }

    suspend fun getMoviesCredits(id: Int): Response<CastCreditsResponse>{
        return api.getMovieCredits(id)
    }

    suspend fun getSimilarMovies(id: Int): Response<SimiliarMoviesResponse>{
        return api.getSimilarMovies(id)
    }
}