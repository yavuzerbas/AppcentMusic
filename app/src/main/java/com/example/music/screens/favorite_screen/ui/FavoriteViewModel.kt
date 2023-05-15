package com.example.music.screens.favorite_screen.ui

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.example.music.MusicApplication
import com.example.music.screens.album_screen.data.db.LikedTrackEntity
import com.example.music.screens.album_screen.model.TrackData
import com.example.music.screens.album_screen.ui.AlbumDetailsViewModel
import com.example.music.screens.album_screen.utils.MusicController
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private val _currentPlayingTrack = mutableStateOf<TrackData?>(null)
    val currentPlayingTrack: State<TrackData?> = _currentPlayingTrack
    private val musicController = MusicController()
    private var lastTrackId = -1
    private val trackDatabase = (application as MusicApplication).trackDatabase
    private val _likedTracks = MutableLiveData<ArrayList<TrackData>>()
    val likedTracks: LiveData<ArrayList<TrackData>> = _likedTracks

    init {
        //fetchLikedTracks()
    }
/*
    private fun fetchLikedTracks() = viewModelScope.launch {
        val x = trackDatabase.trackDao().getLikedTracks()
        val y = ArrayList<TrackData>()
        for (i in x) {
            y.add(TrackData(i.trackId, i.title, i.duration, i.preview, true))
        }
        _likedTracks.postValue(y)
    }
    fun getLikedTracks() : ArrayList<TrackData> {
        val x = trackDatabase.trackDao().getLikedTracks()
        val y = ArrayList<TrackData>()
        for (i in x){
            y.add(TrackData(i.trackId, i.title, i.duration, i.preview, true))
        }
        return y
    }*/

    fun onLikeClick(track: TrackData) = viewModelScope.launch {
        val trackEntity = LikedTrackEntity(
            trackId = track.id,
            title = track.title,
            duration = track.duration,
            preview = track.preview
        )
        if (track.isLiked) {
            trackDatabase.trackDao().insertLikedTrack(trackEntity)
        } else {
            trackDatabase.trackDao().deleteLikedTrack(trackEntity)
        }
    }

    fun playTrack(track: TrackData) {
        if(lastTrackId != track.id) {
            lastTrackId = track.id
        }

        musicController.playTrack(track, onSongComplete = {
            _currentPlayingTrack.value = null
            lastTrackId = -1
        })
    }

    fun stopTrack() {
        musicController.stopTrack()
    }
}

class FavoriteViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
