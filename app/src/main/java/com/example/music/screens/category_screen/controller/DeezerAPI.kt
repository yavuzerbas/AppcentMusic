package com.example.music.screens.category_screen.controller

import com.example.music.screens.category_screen.model.GenresResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface DeezerAPI {
    @GET("genre")
    suspend fun getGenres(): Response<GenresResponse>
}

object DeezerApiHelper {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.deezer.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(DeezerAPI::class.java)

    @Throws(RuntimeException::class)
    suspend fun fetchGenres(): GenresResponse {
        val response = api.getGenres()
        if (response.isSuccessful) {
            return response.body() ?: throw RuntimeException("No data")
        } else {
            throw RuntimeException("API call failed: ${response.errorBody()?.string()}")
        }
    }
}
