package com.example.music.screens.aritst_screen.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.music.ContentBox
import com.example.music.TemplateScreen
import com.example.music.screens.aritst_screen.controller.DeezerArtistApiHelper
import com.example.music.screens.aritst_screen.model.Artist
import com.example.music.screens.aritst_screen.model.ArtistsResponse

@Composable
fun ArtistsScreen(navController: NavController, genreId: String?) {
    val artists = remember { mutableStateOf<ArtistsResponse?>(null) }
    val isLoading = remember { mutableStateOf(true) }
    if(genreId == null){
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
            Text(text = "Genre ID is not found")
        }
    }
    else{
        LaunchedEffect(key1 = genreId) {
            try {
                isLoading.value = true
                artists.value = DeezerArtistApiHelper.fetchArtists(genreId)
            } catch (e: Exception) {
                // handle error
            } finally {
                isLoading.value = false
            }
        }
        if(isLoading.value){
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        else{
            TemplateScreen(title = "Artists", content = {
                ArtistList(artists = artists.value!!)
            }, contentIsEmpty = artists.value == null)
        }
    }
}
@Composable
fun ArtistList(artists: ArtistsResponse) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.padding(4.dp),
        contentPadding = PaddingValues(4.dp)
    ) {
        items(artists.data) { artist ->
            ArtistBox(artist = artist,onclick = {
            })
        }
    }
}
@Composable
fun ArtistBox(artist: Artist,onclick: () -> Unit) {
    ContentBox(pictureText = artist.name, pictureUrl = artist.pictureBig, onclick = onclick)
}