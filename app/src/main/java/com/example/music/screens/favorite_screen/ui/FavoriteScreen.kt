package com.example.music

import android.app.Application
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import com.example.music.screens.favorite_screen.ui.FavoriteViewModel
import com.example.music.screens.favorite_screen.ui.FavoriteViewModelFactory


@Composable
fun FavoriteScreen(navController: NavHostController, application: Application) {
    val viewModel = ViewModelProvider(LocalContext.current as ComponentActivity, FavoriteViewModelFactory(application)).get(
        FavoriteViewModel::class.java)


    TemplateScreen(navController = navController, title = "Favorites Screen",
        content = {
            /*LazyColumn {
                items(viewModel.likedTracks) { track ->
                    TrackCard("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRhG_cZjxXIlwfsjseD7-LMSMzWI7txguoSLjCbwM85&s",track,
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
            }*/
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Will be completed later!", color = Color.White)
            }
        },
        contentIsEmpty = false)
}

