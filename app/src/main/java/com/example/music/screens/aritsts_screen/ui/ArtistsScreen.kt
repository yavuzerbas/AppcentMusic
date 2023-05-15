package com.example.music.screens.aritsts_screen.ui

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.music.ContentBox
import com.example.music.LoadingScreen
import com.example.music.TemplateScreen
import com.example.music.screens.aritsts_screen.model.Artist
import com.example.music.screens.aritsts_screen.model.ArtistsResponse
import com.google.gson.Gson

import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ArtistsScreen(navController: NavController, genreId: String?,categoryName: String?) {
    val viewModel: ArtistsViewModel = viewModel() // Get a reference to your ViewModel
    val categoryParsed: String
    if(categoryName != null){
        categoryParsed = categoryName.replace("_"," ").replace(",","/")
    }
    else{
        categoryParsed = "Artists"
    }
    if(genreId == null){
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
            Text(text = "Genre ID is not found")
        }
    }
    else{
        LaunchedEffect(key1 = genreId) {
            viewModel.fetchArtists(genreId)
        }
        if(viewModel.isLoading){
            LoadingScreen(navController=navController,categoryParsed)
        }
        else{
            TemplateScreen(
                title = categoryParsed,
                content = {
                    ArtistList(navController= navController, artists = viewModel.artists!!)
                },
                contentIsEmpty = viewModel.artists == null,
                navController = navController
            )
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