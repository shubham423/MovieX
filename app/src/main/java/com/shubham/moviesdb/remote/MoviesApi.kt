package com.shubham.moviesdb.remote

import com.shubham.moviesdb.response.*
import com.shubham.moviesdb.utils.Constants.DEFAULT_QUERY
import com.shubham.moviesdb.utils.Constants.TMDB_API_KEY
import com.shubham.moviesdb.utils.Constants.TV_APPEND_QUERY
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
    ): Response<Movie>


    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") movie_id: Int,
        @Query ("api_key") apiKey : String = TMDB_API_KEY
    ): Response<CastCreditsResponse>

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") movie_id: Int,
        @Query ("api_key") apiKey : String = TMDB_API_KEY
    ): Response<SimiliarMoviesResponse>


    @GET("person/{person_id}")
    suspend fun getActor(
        @Path("person_id") person_id: Int,
        @Query ("api_key") apiKey : String = TMDB_API_KEY
    ): Response<ActorResponse>

    @GET("tv/{category}")
    suspend fun getTvShowByCategory(
        @Path("category") category: String,
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = TMDB_API_KEY
    ): Response<TvShowResponse>

    @GET("tv/{tv_id}")
    suspend fun getTvShowDetails(
        @Path("tv_id") id: Int,
        @Query("append_to_response") appendQuery: String = TV_APPEND_QUERY,
        @Query("api_key") apiKey: String = TMDB_API_KEY
    ): Response<TvShow>
}