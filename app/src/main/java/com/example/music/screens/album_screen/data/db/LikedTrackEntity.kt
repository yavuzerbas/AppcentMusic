package com.example.music.screens.album_screen.data.db

//import androidx.room.ColumnInfo
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LikedTrackEntity (
    @PrimaryKey val trackId: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "duration") val duration: Int,
    @ColumnInfo(name = "preview") val preview: String,
)