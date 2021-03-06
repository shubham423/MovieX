package com.shubham.moviesdb.response


import com.google.gson.annotations.SerializedName

data class CreditsResponse(
    @SerializedName("cast")
    val cast: List<Cast>,
    @SerializedName("crew")
    val crew: List<Crew>
)