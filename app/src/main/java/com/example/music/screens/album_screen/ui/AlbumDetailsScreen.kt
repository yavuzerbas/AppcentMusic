package com.example.music.screens.album_screen.ui

import android.app.Application
import androidx.activity.ComponentActivity
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.example.music.MockImage
import com.example.music.TemplateScreen
import com.example.music.screens.album_screen.model.TrackData

@Composable
fun AlbumDetailsScreen(navController: NavController, encodedJsonAlbum: String,application: Application){
    val viewModel = ViewModelProvider(LocalContext.current as ComponentActivity, AlbumDetailsViewModelFactory(application)).get(AlbumDetailsViewModel::class.java)
    val albumDetailsResponse = viewModel.decodeAndDeserializeJson(encodedJsonAlbum)

    DisposableEffect(Unit) {
        onDispose {
            viewModel.stopTrack()
        }
    }
    TemplateScreen(title = albumDetailsResponse.albumName, content = {LazyColumn {
        items(albumDetailsResponse.tracks.data) { track ->
            viewModel.backgroundColors.addColor(track.id)
            TrackCard(albumDetailsResponse.albumPicture,track,
                onclick = {
                    viewModel.playTrack(track)
                },
                onLikeClick = {
                    track.isLiked = !track.isLiked
                    viewModel.onLikeClick(track)
                },
                isPlaying = viewModel.currentPlayingTrack.value?.id == track.id,
                viewModel = viewModel
            )
        }
    }}, contentIsEmpty = albumDetailsResponse.tracks.data.isEmpty(),
        navController = navController)
}
@Composable
fun TrackCard(image: String, track: TrackData, onclick: () -> Unit, onLikeClick: () -> Unit,isPlaying: Boolean,viewModel: AlbumDetailsViewModel){
    var isLiked by remember { mutableStateOf(track.isLiked) } // state to keep track of whether the track is liked or not
    //var backgroundColor by remember{ mutableStateOf(Color.DarkGray) }
    //val backgroundColor = if (isPlaying) Color.Red else Color.DarkGray
    val trackColor = viewModel.backgroundColors.getColor(track.id)
    Box(
        modifier = Modifier
            .padding(8.dp)
            .background(trackColor)
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp)) // For curvy edges
            .border(1.dp, Color.White, RoundedCornerShape(8.dp))
            .clickable {
                onclick()
                //backgroundColor = Color.Red
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