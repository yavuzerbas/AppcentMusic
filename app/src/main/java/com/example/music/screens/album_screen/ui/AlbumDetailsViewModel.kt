package com.example.music.screens.album_screen.ui

import android.app.Application
import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.music.screens.album_screen.data.db.LikedTrackEntity
import com.example.music.screens.album_screen.model.AlbumDetailsResponse
import com.example.music.screens.album_screen.model.BackgroundColorsOfTrackCards
import com.example.music.screens.album_screen.model.TrackData
import com.example.music.screens.album_screen.utils.MusicController
import com.google.gson.Gson
import kotlinx.coroutines.launch

class AlbumDetailsViewModel(application: Application)  : AndroidViewModel(application) {

    private val _currentPlayingTrack = mutableStateOf<TrackData?>(null)
    val currentPlayingTrack: State<TrackData?> = _currentPlayingTrack
    val backgroundColors = BackgroundColorsOfTrackCards()
    private val musicController = MusicController()
    private var lastTrackId = -1
    fun onLikeClick(track: TrackData) = viewModelScope.launch {
        val trackEntity = LikedTrackEntity(
            trackId = track.id,
            title = track.title,
            duration = track.duration,
            preview = track.preview
        )
        /*if (track.isLiked) {
            trackDatabase.trackDao().insert(trackEntity)
        } else {
            trackDatabase.trackDao().delete(trackEntity)
        }*/
    }
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
class AlbumDetailsViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AlbumDetailsViewModel::class.java)) {
            return AlbumDetailsViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}



