package com.shubham.moviesdb.remote

import com.shubham.moviesdb.response.*
import retrofit2.Response
import javax.inject.Inject

class TvShowsRepository @Inject constructor(private val api: MoviesApi) {

    suspend fun getTvShowsByCategory(category: String): Response<TvShowResponse> {
        return api.getTvShowByCategory(category)
    }
    suspend fun getTvShowDetail(id: Int): Response<TvShow> {
        return api.getTvShowDetails(id)
    }
}