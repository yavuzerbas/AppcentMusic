package com.example.music.screens.album_screen.model

import com.google.gson.annotations.SerializedName

data class AlbumDetailsResponse(
    @SerializedName("tracks")
    val tracks: Tracks,

    @SerializedName("release_date")
    val releaseDate: String,

    var albumName: String,

    var albumPicture: String
)

data class Tracks(
    @SerializedName("data")
    val data: List<TrackData>
)

class TrackData(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("duration")
    val duration: Int,

    @SerializedName("preview")
    val preview: String,

    var isLiked: Boolean = false
){
    fun getDurationInMinutes(): String{
        val minutes = duration / 60
        val seconds = duration % 60
        return "$minutes:$seconds"
    }
    fun getTitleDeletingAfterEncounterParentheses(): String{
        val index = title.indexOf("(")
        return if(index == -1) title else title.substring(0, index)
    }
}
