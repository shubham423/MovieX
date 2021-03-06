package com.shubham.moviesdb.remote

import android.util.Log
import androidx.lifecycle.LiveData
import com.shubham.moviesdb.local.database.MovieDao
import com.shubham.moviesdb.local.database.MovieEntity
import com.shubham.moviesdb.response.*
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class MoviesRepository @Inject constructor(private val api: MoviesApi ,private val movieDao: MovieDao) {

    suspend fun getMoviesByCategory(category: String): Response<MovieResponse>{
        return api.getMovieResponse(category)
    }
    suspend fun getMoviesById(id: Int): Response<Movie>{
        return SafeApiRequest.apiRequest { api.getMovieDetails(id)  }
    }

    suspend fun getMoviesCredits(id: Int): Response<CastCreditsResponse>{
        return SafeApiRequest.apiRequest { api.getMovieCredits(id) }
    }

    suspend fun getSimilarMovies(id: Int): Response<SimiliarMoviesResponse>{
        return SafeApiRequest.apiRequest { api.getSimilarMovies(id) }
    }

    suspend fun getActor(castId: Int): Response<ActorResponse>{
        return SafeApiRequest.apiRequest { api.getActor(castId) }
    }

    suspend fun searchMovie(query: String): Response<MovieResponse>{
        return SafeApiRequest.apiRequest { api.searchMovies(query) }
    }

    suspend fun addFavoriteMovie(movie: MovieEntity) = movieDao.insert(movie)

    suspend fun removeFavoriteMovie(movie: MovieEntity) = movieDao.delete(movie)

    suspend fun getFavoriteMovies(): List<MovieEntity> {
        Log.d("Bookmark repo","${movieDao.getFavoriteMovies()}")
        return movieDao.getFavoriteMovies()
    }

    suspend fun isMovieExist(id: Int): Boolean {
        Log.d("Bookmark repo isexist","${movieDao.isMovieExists(id)}")
        return movieDao.isMovieExists(id)
    }
}