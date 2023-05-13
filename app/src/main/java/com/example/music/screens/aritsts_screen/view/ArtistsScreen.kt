package com.example.music.screens.aritsts_screen.view

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import com.example.music.LoadingScreen
import com.example.music.TemplateScreen
import com.example.music.screens.aritsts_screen.controller.DeezerArtistApiHelper
import com.example.music.screens.aritsts_screen.model.Artist
import com.example.music.screens.aritsts_screen.model.ArtistsResponse
import com.google.gson.Gson

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
            LoadingScreen("Artists")
        }
        else{
            TemplateScreen(title = "Artists", content = {
                ArtistList(navController= navController,artists = artists.value!!)
            }, contentIsEmpty = artists.value == null)
        }
    }
}
@Composable
fun ArtistList(navController: NavController, artists: ArtistsResponse) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.padding(4.dp),
        contentPadding = PaddingValues(4.dp)
    ) {
        items(artists.data) { artist ->
            ArtistBox(artist = artist,onclick = {
                // Convert myGenres to a JSON string
                val gson = Gson()
                val jsonArtist = gson.toJson(artist)

                // Encode the JSON string
                val encodedJsonArtist = Uri.encode(jsonArtist)

                // Navigate to CategoryScreen with myGenres as an argument
                navController.navigate("artist_detail_screen/$encodedJsonArtist")
            })
        }
    }
}
@Composable
fun ArtistBox(artist: Artist,onclick: () -> Unit) {
    ContentBox(pictureText = artist.name, pictureUrl = artist.pictureBig, onclick = onclick)
}