package com.example.music.screens.aritst_screen.model

import com.google.gson.annotations.SerializedName

data class ArtistsResponse(
    @SerializedName("data")
    val data: List<Artist>
)

data class Artist(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("picture")
    val picture: String,

    @SerializedName("picture_small")
    val pictureSmall: String,

    @SerializedName("picture_medium")
    val pictureMedium: String,

    @SerializedName("picture_big")
    val pictureBig: String,

    @SerializedName("picture_xl")
    val pictureXl: String,

    @SerializedName("radio")
    val radio: Boolean,

    @SerializedName("tracklist")
    val tracklist: String,

    @SerializedName("type")
    val type: String
)