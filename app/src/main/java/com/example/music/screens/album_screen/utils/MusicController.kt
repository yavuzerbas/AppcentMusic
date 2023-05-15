package com.example.music.screens.album_screen.utils

import android.media.MediaPlayer
import androidx.compose.ui.graphics.Color
import com.example.music.screens.album_screen.model.TrackData

class MusicController {
    private var mediaPlayer: MediaPlayer? = null
    var currentTrack: TrackData? = null
    fun playTrack(track: TrackData,onSongComplete: () -> Unit) {
        if(mediaPlayer != null && mediaPlayer!!.isPlaying && currentTrack == track) {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null
            currentTrack = null
            //track.backgroundColor = Color.DarkGray
            return
        }
        // Stop and release the current media player, if it exists
        mediaPlayer?.stop()
        mediaPlayer?.release()
        currentTrack = track
        //track.backgroundColor = Color.Red
        // Create a new media player for the new track
        mediaPlayer = MediaPlayer().apply {
            setDataSource(track.preview)
            setOnPreparedListener { start() }
            setOnCompletionListener {
                mediaPlayer = null
                onSongComplete()
            }
            prepareAsync()
        }
    }
    fun stopTrack() {
        mediaPlayer?.release()
        mediaPlayer = null
    }
    private fun isPlaying(): Boolean {
        return mediaPlayer?.isPlaying ?: false
    }
}
