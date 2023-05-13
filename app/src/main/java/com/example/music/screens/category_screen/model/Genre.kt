package com.example.music.screens.category_screen.model

import com.google.gson.annotations.SerializedName

data class GenresResponse(
    @SerializedName("data")
    val data: List<Genre>
)

data class Genre(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

/*    @SerializedName("picture")
    val picture: String,

    @SerializedName("picture_small")
    val pictureSmall: String,

    @SerializedName("picture_medium")
    val pictureMedium: String,*/

    @SerializedName("picture_big")
    val pictureBig: String,

/*
    @SerializedName("picture_xl")
    val pictureXl: String,
*/

    @SerializedName("type")
    val type: String
)

