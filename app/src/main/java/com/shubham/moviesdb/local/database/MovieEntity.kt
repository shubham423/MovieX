package com.shubham.moviesdb.local.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.shubham.moviesdb.response.Movie
import com.shubham.moviesdb.response.VideosResponse

@Entity(tableName = "favorite_movie")
data class MovieEntity(
    @Expose @SerializedName("id") @PrimaryKey val id: Int,
    @Expose val title: String,
    @Expose val overview: String,
    @Expose @SerializedName("poster_path") @ColumnInfo(name = "poster_path") val posterPath: String,
    @Expose @SerializedName("release_date") @ColumnInfo(name = "release_date") val releaseDate: String,
    @Expose @SerializedName("vote_average") val rating: Float,
) {
    @Ignore
    @Expose
    @SerializedName("vote_count")
    var ratingVotes: Int = 0

    @Ignore
    @Expose
    @SerializedName("runtime")
    var duration: Int = 0

    @Ignore
    @Expose
    @SerializedName("genres")
    var genres: List<Movie.Genre> = emptyList()

    @Ignore
    @Expose
    var videos: VideosResponse? = null


}

