package com.shubham.moviesdb.local.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movie")
data class MovieEntity(
    @PrimaryKey val id: Int,
     val title: String,
     val overview: String,
     val posterPath: String,
     val releaseDate: String,
     val rating: Float,
)

