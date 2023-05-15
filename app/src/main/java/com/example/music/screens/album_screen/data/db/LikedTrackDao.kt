package com.example.music.screens.album_screen.data.db
import androidx.room.*
@Dao
interface LikedTrackDao {
/*    @Query("SELECT * FROM LikedTrackEntity")
    fun getLikedTracks(): ArrayList<LikedTrackEntity>*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLikedTrack(likedTrackEntity: LikedTrackEntity)
    @Delete
    fun deleteLikedTrack(likedTrackEntity: LikedTrackEntity)
}