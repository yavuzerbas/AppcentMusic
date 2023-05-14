package com.example.music.screens.artist_detail_screen.ui

import AlbumsResponse
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.music.screens.album_screen.data.DeezerAlbumDetailsApiHelper
import com.example.music.screens.artist_detail_screen.data.DeezerAlbumApiHelper
import kotlinx.coroutines.Job
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

class ArtistDetailViewModel : ViewModel() {
    val albums = mutableStateOf<AlbumsResponse?>(null)
    val isLoading = mutableStateOf(true)

    fun loadAlbums(artistId: String) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                val fetchedAlbums = DeezerAlbumApiHelper.fetchAlbums(artistId)
                fetchedAlbums.removeDuplicates()
                albums.value = fetchedAlbums
                // Create a list to hold the Job objects returned by launch
                val jobs = mutableListOf<Job>()

                for (album in albums.value!!.data) {
                    // Start a new coroutine for each album
                    jobs += launch {
                        val fetchedAlbumDetails = DeezerAlbumDetailsApiHelper.fetchAlbumDetails(album.album.id.toString())
                        album.album.albumDetailsResponse = fetchedAlbumDetails
                    }
                }
                // Wait for all coroutines to complete
                jobs.joinAll()
            } catch (e: Exception) {
                // handle error
            } finally {
                isLoading.value = false
            }
        }
    }
}
