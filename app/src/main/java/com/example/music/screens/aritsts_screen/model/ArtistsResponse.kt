package com.example.music.screens.aritsts_screen.model

import com.google.gson.annotations.SerializedName

data class ArtistsResponse(
    @SerializedName("data")
    val data: List<Artist>,

    val categoryName: String
)
data class Artist(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("picture_big")
    val pictureBig: String,
)