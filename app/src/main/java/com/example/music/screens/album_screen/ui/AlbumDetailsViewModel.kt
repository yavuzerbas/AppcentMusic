package com.example.music.screens.album_screen.ui

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.music.screens.album_screen.model.AlbumDetailsResponse
import com.example.music.screens.album_screen.model.BackgroundColorsOfTrackCards
import com.example.music.screens.album_screen.model.TrackData
import com.example.music.screens.album_screen.utils.MusicController
import com.google.gson.Gson

class AlbumDetailsViewModel : ViewModel() {

    private val _currentPlayingTrack = mutableStateOf<TrackData?>(null)
    val currentPlayingTrack: State<TrackData?> = _currentPlayingTrack
    val backgroundColors = BackgroundColorsOfTrackCards()
    private val musicController = MusicController()
    private var lastTrackId = -1

    fun playTrack(track: TrackData) {
        if(lastTrackId != track.id) {
            backgroundColors.makeColorDarkGray(lastTrackId)
            lastTrackId = track.id
        }

        musicController.playTrack(track, onSongComplete = {
            _currentPlayingTrack.value = null
            backgroundColors.makeColorDarkGray(track.id)
            lastTrackId = -1
        })

        backgroundColors.toggleColor(track.id)
    }

    fun stopTrack() {
        musicController.stopTrack()
    }

    fun decodeAndDeserializeJson(encodedJsonAlbum: String): AlbumDetailsResponse {
        val jsonAlbum = Uri.decode(encodedJsonAlbum)
        val gson = Gson()
        return gson.fromJson(jsonAlbum, AlbumDetailsResponse::class.java)
    }
}

