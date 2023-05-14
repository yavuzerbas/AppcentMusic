package com.example.music.screens.album_screen.utils

import android.media.MediaPlayer

class MusicController {
    private var mediaPlayer: MediaPlayer? = null
    private var currentTrack: String? = null
    fun playTrack(url: String) {
        if(mediaPlayer != null && mediaPlayer!!.isPlaying && currentTrack == url) {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null
            currentTrack = null
            return
        }
        // Stop and release the current media player, if it exists
        mediaPlayer?.stop()
        mediaPlayer?.release()
        currentTrack = url
        // Create a new media player for the new track
        mediaPlayer = MediaPlayer().apply {
            setDataSource(url)
            setOnPreparedListener { start() }
            setOnCompletionListener { mediaPlayer = null }
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
