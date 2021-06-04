package com.shubham.moviesdb.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SimilarResponse(
    @Expose @SerializedName("page") val page: Int,
    @Expose @SerializedName("results") val similar: List<Similar>,
    @Expose @SerializedName("total_pages") val totalPages: Int,
    @Expose @SerializedName("total_results") val totalResults: Int

)