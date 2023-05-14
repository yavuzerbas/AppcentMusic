package com.example.music.screens.album_screen.model

import com.google.gson.annotations.SerializedName

data class AlbumDetailsResponse(
    @SerializedName("tracks")
    val tracks: Tracks,

    @SerializedName("release_date")
    val releaseDate: String
)

data class Tracks(
    @SerializedName("data")
    val data: List<TrackData>
)

data class TrackData(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("duration")
    val duration: Int,

    @SerializedName("preview")
    val preview: String
)
