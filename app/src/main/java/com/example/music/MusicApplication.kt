package com.example.music

import android.app.Application
import androidx.room.Room
import com.example.music.screens.album_screen.data.db.LikedTrackDatabase

class MusicApplication : Application() {

    // Instance of the Room database
    lateinit var trackDatabase: LikedTrackDatabase

    override fun onCreate() {
        super.onCreate()

        // Initialize the Room database
        trackDatabase = Room.databaseBuilder(
            applicationContext,
            LikedTrackDatabase::class.java, "track-database"
        ).build()
    }
}