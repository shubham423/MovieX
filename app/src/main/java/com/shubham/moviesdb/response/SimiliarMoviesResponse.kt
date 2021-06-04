package com.shubham.moviesdb.response


import com.google.gson.annotations.SerializedName

data class SimiliarMoviesResponse(
    @SerializedName("page")
    val page: Int?,
    @SerializedName("results")
    val results: List<SimilarMovie?>?,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?
)