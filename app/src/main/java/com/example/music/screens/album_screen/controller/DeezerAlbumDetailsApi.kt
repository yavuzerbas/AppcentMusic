package com.example.music.screens.album_screen.controller

import com.example.music.screens.album_screen.model.AlbumDetailsResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface DeezerAlbumDetailsApi {
    @GET("album/{album_id}")
    suspend fun getAlbumDetails(@Path("album_id") albumId: String): Response<AlbumDetailsResponse>
}
object DeezerAlbumDetailsApiHelper {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.deezer.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(DeezerAlbumDetailsApi::class.java)

    @Throws(RuntimeException::class)
    suspend fun fetchAlbumDetails(albumId: String): AlbumDetailsResponse {
        val response = api.getAlbumDetails(albumId)
        if (response.isSuccessful) {
            return response.body() ?: throw RuntimeException("No data")
        } else {
            throw RuntimeException("API call failed: ${response.errorBody()?.string()}")
        }
    }
}
