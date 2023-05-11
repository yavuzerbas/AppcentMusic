package com.example.music.song_playground

data class Song(
    val id: Int,
    val name: String,
    val duration: Int,  // Duration in seconds
    val url: String,
)