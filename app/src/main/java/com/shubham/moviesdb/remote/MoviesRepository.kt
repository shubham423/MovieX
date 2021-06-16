package com.shubham.moviesdb.remote

import androidx.lifecycle.LiveData
import com.shubham.moviesdb.local.database.MovieDao
import com.shubham.moviesdb.local.database.MovieEntity
import com.shubham.moviesdb.response.*
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class MoviesRepository @Inject constructor(private val api: MoviesApi,private val movieDao: MovieDao) {


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

    suspend fun addFavoriteMovie(movie: MovieEntity) = movieDao.insert(movie)

    suspend fun removeFavoriteMovie(movie: MovieEntity) = movieDao.delete(movie)
    suspend fun isFavoriteMovie(movieId: Int): Boolean=movieDao.isMovieExists(movieId)
    
    fun getFavoriteMovies(): LiveData<List<MovieEntity>> {
        return movieDao.getFavoriteMovies()
    }
}