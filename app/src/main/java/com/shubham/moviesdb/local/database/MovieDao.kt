package com.shubham.moviesdb.local.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.shubham.moviesdb.response.Movie

@Dao
interface MovieDao {

    @Query("SELECT * FROM favorite_movie ORDER BY id DESC")
    fun getFavoriteMovies(): LiveData<List<MovieEntity>>

    @Query("SELECT EXISTS(SELECT * FROM favorite_movie WHERE id=:id)")
    suspend fun isMovieExists(id: Int): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie:MovieEntity)

    @Delete
    suspend fun delete(movie: MovieEntity)

}