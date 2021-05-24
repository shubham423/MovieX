package com.shubham.moviesdb.remote

import com.shubham.moviesdb.response.MovieDetailsResponse
import com.shubham.moviesdb.response.MovieResponse
import com.shubham.moviesdb.utils.Constants.DEFAULT_QUERY
import com.shubham.moviesdb.utils.Constants.TMDB_API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {

    @GET("movie/{category}")
    suspend fun getMovieResponse(
        @Path("category") category: String,
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = TMDB_API_KEY
    ): Response<MovieResponse>


    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("api_key") apiKey: String = TMDB_API_KEY
    ): Response<MovieResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
            @Path("movie_id") id: Int,
            @Query("append_to_response") appendQuery: String = DEFAULT_QUERY,
            @Query("api_key") apiKey: String = TMDB_API_KEY
    ): Response<MovieDetailsResponse>
}