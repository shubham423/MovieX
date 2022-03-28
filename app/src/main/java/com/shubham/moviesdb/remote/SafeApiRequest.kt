package com.shubham.moviesdb.remote


import retrofit2.Response


 object SafeApiRequest {

    suspend fun <T: Any> apiRequest(call : suspend() -> Response<T>): Response<T> {

        val response = call.invoke()

        if (response.isSuccessful && response.body() != null) {
            return Response.success(response.body()!!)
        }
        else {
            throw Exception(response.code().toString())
        }

    }

}