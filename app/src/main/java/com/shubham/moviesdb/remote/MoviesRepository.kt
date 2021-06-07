package com.shubham.moviesdb.remote

import com.shubham.moviesdb.response.*
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

    suspend fun getActor(castId: Int): Response<ActorResponse>{
        return api.getActor(castId)
    }

    suspend fun searchMovie(query: String): Response<MovieResponse>{
        return api.searchMovies(query)
    }
}