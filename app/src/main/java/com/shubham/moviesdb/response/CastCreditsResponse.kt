package com.shubham.moviesdb.response


import com.google.gson.annotations.SerializedName

data class CastCreditsResponse(
    @SerializedName("cast")
    val cast: List<Cast?>?,
    @SerializedName("crew")
    val crew: List<Crew?>?,
    @SerializedName("id")
    val id: Int?
)