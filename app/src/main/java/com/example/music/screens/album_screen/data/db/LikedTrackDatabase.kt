package com.example.music.screens.album_screen.data.db
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LikedTrackEntity::class], version = 1)
abstract class LikedTrackDatabase : RoomDatabase() {
    abstract fun trackDao(): LikedTrackDao
}