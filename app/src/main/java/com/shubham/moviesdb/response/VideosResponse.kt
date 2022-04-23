package com.shubham.moviesdb.response

import com.google.gson.annotations.SerializedName

data class VideosResponse(
    @SerializedName("results")
    val videos: List<Video>
)