package com.example.music.screens.artist_detail_screen.view

import Album
import AlbumsResponse
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.music.LoadingScreen
import com.example.music.TemplateScreen
import com.example.music.screens.album_screen.controller.DeezerAlbumDetailsApiHelper
import com.example.music.screens.aritsts_screen.model.Artist
import com.example.music.screens.artist_detail_screen.controller.DeezerAlbumApiHelper
import com.example.music.ui.theme.YTMusicPurple
import com.google.gson.Gson
import kotlinx.coroutines.Job
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch


@Composable
fun ArtistDetailScreen(navController: NavController, encodedJsonArtist: String) {
    // Decode the JSON string
    val jsonArtist = Uri.decode(encodedJsonArtist)
    // Deserialize the JSON string back to GenresResponse object
    val gson = Gson()
    val artist = gson.fromJson(jsonArtist, Artist::class.java)

    val albums = remember { mutableStateOf<AlbumsResponse?>(null) }
    val isLoading = remember { mutableStateOf(true) }

    LaunchedEffect(key1 = artist.id) {
        try {
            isLoading.value = true
            val fetchedAlbums = DeezerAlbumApiHelper.fetchAlbums(artist.id)
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
    if(isLoading.value){
        LoadingScreen(artist.name)
    }
    else{
        TemplateScreen(title = artist.name, content = {
            Column{
                ArtistDetailScreenBody(navController,artist = artist,albums = albums.value!!)
            }
        }, contentIsEmpty = (albums.value == null) || (albums.value!!.data.isEmpty()) )
    }
}
@Composable
fun ArtistPicture(artist: Artist){
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(160.dp)
        .padding(bottom = 8.dp)){
        AsyncImage(
            model = artist.pictureBig,
            contentDescription = "Artist Image",
            contentScale = androidx.compose.ui.layout.ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize()
                //.padding(end = 8.dp)
                .align(Alignment.Center),

            alpha = 0.5f
        )
        AsyncImage(
            model = artist.pictureBig,
            contentDescription = "Artist Image",
            //contentScale = androidx.compose.ui.layout.ContentScale.FillBounds,
            modifier = Modifier
                //.fillMaxSize()
                .padding(end = 8.dp)
                .align(Alignment.Center)
        )

    }
}
@Composable
fun ArtistDetailScreenBody(navController: NavController, artist: Artist, albums: AlbumsResponse) {
    val x = albums.data;
    LazyColumn(Modifier.background(color = YTMusicPurple)/*.fillMaxHeight()*/,content = {
        item {
            ArtistPicture(artist = artist)
        }
        items(x) { album ->
            AlbumCard(album = album.album, onclick = {
                // Navigate to the album details screen
                val gson = Gson()
                val jsonAlbum = gson.toJson(album.album)
                val encodedJsonAlbum = Uri.encode(jsonAlbum)
                navController.navigate("album_details_screen/$encodedJsonAlbum")
            })
        }
    })
}
@Composable
fun AlbumCard(album: Album, onclick : () -> Unit) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .background(Color.DarkGray)
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp)) // For curvy edges
            .border(1.dp, Color.White, RoundedCornerShape(8.dp))
            .clickable { onclick() }// For white border
        //elevation = 8.dp
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = album.coverSmall,
                contentDescription = "Artist Image",
                modifier = Modifier
                    .size(60.dp)
                    .fillMaxHeight()
            )
            Column{
                Text(text = album.title, color = Color.White, modifier = Modifier.padding(start = 20.dp))
                if(album.albumDetailsResponse != null)
                    Text(text = album.albumDetailsResponse!!.releaseDate, color = Color.White, modifier = Modifier.padding(start = 20.dp, top = 8.dp), fontStyle = androidx.compose.ui.text.font.FontStyle.Italic)
                else
                    Text(text = "", color = Color.White, modifier = Modifier.padding(start = 20.dp, top = 4.dp), fontStyle = androidx.compose.ui.text.font.FontStyle.Italic)
            }
        }
    }
}