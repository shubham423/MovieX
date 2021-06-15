package com.shubham.moviesdb.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shubham.moviesdb.response.Movie


@Database(
    entities = [MovieEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

}