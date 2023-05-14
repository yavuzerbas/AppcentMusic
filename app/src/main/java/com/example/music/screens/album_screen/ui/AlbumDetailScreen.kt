package com.example.music.screens.album_screen.ui

import com.example.music.screens.album_screen.utils.MusicController
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.music.TemplateScreen
import com.example.music.screens.album_screen.model.AlbumDetailsResponse
import com.example.music.screens.album_screen.model.TrackData
import com.google.gson.Gson

val colorList = listOf(
    Color(0xFFE57373), // Light Red
    Color(0xFF81C784), // Light Green
    Color(0xFF64B5F6), // Light Blue
    Color(0xFFFFF176), // Light Yellow
    Color(0xFF4DD0E1), // Light Cyan
    Color(0xFFF48FB1), // Light Magenta
    Color(0xFFE0E0E0), // Light Gray
    Color(0xFFAED581), // Light Lime
    Color(0xFF7986CB), // Light Indigo
    Color(0xFFDCE775), // Lime Yellow
)

// Variable to keep track of the current color index
var colorIndex = 0
@Composable
fun AlbumDetailsScreen(navController: NavController, encodedJsonAlbum: String) {

    val musicController = MusicController()
    // Decode the JSON string
    val jsonAlbum = Uri.decode(encodedJsonAlbum)
    // Deserialize the JSON string back to GenresResponse object
    val gson = Gson()
    val albumDetailsResponse = gson.fromJson(jsonAlbum, AlbumDetailsResponse::class.java)
    // This will stop the track when the screen is left
    DisposableEffect(Unit) {
        onDispose {
            musicController.stopTrack()
        }
    }
    TemplateScreen(title = albumDetailsResponse.albumName, content = {LazyColumn {
        items(albumDetailsResponse.tracks.data) { track ->
            TrackCard(albumDetailsResponse.albumPicture,track,musicController,
                onclick = {
                    // Play or stop the track when clicked
                    musicController.playTrack(track.preview)
            },
                onLikeClick = {
                    track.isLiked = !track.isLiked
                })
        }
    }},contentIsEmpty = albumDetailsResponse.tracks.data.isEmpty())

}

@Composable
fun TrackCard(image: String, track: TrackData, musicController: MusicController, onclick: () -> Unit, onLikeClick: () -> Unit){
    var isLiked by remember { mutableStateOf(track.isLiked) } // state to keep track of whether the track is liked or not

    Box(
        modifier = Modifier
            .padding(8.dp)
            .background(Color.DarkGray)
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp)) // For curvy edges
            .border(1.dp, Color.White, RoundedCornerShape(8.dp))
            .clickable {
                onclick()
            }// For white border
        //elevation = 8.dp
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            MockImage(image, text = track.getTitleDeletingAfterEncounterParentheses())
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = track.title, color = Color.White, modifier = Modifier.padding(start = 20.dp))
                Text(text = track.getDurationInMinutes(), color = Color.White, modifier = Modifier.padding(start = 20.dp, top = 8.dp), fontStyle = FontStyle.Italic)
            }
            IconButton(onClick = { isLiked = !isLiked }) {
                Icon(
                    imageVector = if (isLiked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = "Like Button",
                    tint = if (isLiked) Color.Red else Color.White
                )
            }
        }
    }
}


@Composable
fun MockImage(image: String,text: String) {
    // Get the current color from the list
    val currentColor = colorList[colorIndex]

    // Increment the index for the next call
    colorIndex = (colorIndex + 1) % colorList.size
    Box(
        modifier = Modifier
            .size(60.dp)
            .fillMaxHeight()
            .background(
                color = currentColor
            ) // range 0.0 to 0.5)
        //elevation = 8.dp
    ) {
        AsyncImage(
            model = image,
            contentDescription = "Artist Image",
            contentScale = androidx.compose.ui.layout.ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize()
                //.padding(end = 8.dp)
                .align(Alignment.Center),

            alpha = 0.5f
        )
        Text(text = text,
            modifier = Modifier.padding(1.dp).align(Alignment.Center),
            color = Color.Black,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Bold,
            fontSize = 10.sp)
    }
}
