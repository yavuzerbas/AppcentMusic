package com.example.music.screens.aritsts_screen.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.music.screens.aritsts_screen.data.DeezerArtistApiHelper
import com.example.music.screens.aritsts_screen.model.ArtistsResponse
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class ArtistsViewModel : ViewModel() {
    var artists by mutableStateOf<ArtistsResponse?>(null)
    var isLoading by mutableStateOf(true)

    fun fetchArtists(genreId: String) {
        viewModelScope.launch {
            try {
                isLoading = true
                artists = DeezerArtistApiHelper.fetchArtists(genreId)
            } catch (e: Exception) {
                // handle error
            } finally {
                isLoading = false
            }
        }
    }
}
