package com.example.music.song_playground

import android.media.MediaPlayer
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun SongPlayground() {
    val mediaPlayer = remember { MediaPlayer() }
    val isPlaying = remember { mutableStateOf(false) }

    SongPlayerScreen(isPlaying, mediaPlayer)
}
@Composable
fun SongPlayerScreen(isPlaying: MutableState<Boolean>, mediaPlayer: MediaPlayer) {
    val song = Song(1, "Song Name", 170,"https://cdns-preview-8.dzcdn.net/stream/c-885610b11987959b3ad61298d63d32bb-4.mp3")
    var progress by remember { mutableStateOf(0f) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = song, block = {
        mediaPlayer.reset()
        mediaPlayer.setDataSource(song.url)
        mediaPlayer.setOnPreparedListener {
            progress = 0f
            isPlaying.value = false
            //it.start()  // start playback after mediaPlayer is prepared
        }
        mediaPlayer.setOnCompletionListener {
            isPlaying.value = false
        }
        mediaPlayer.prepareAsync()  // this is non-blocking
    })
    DisposableEffect(key1 = song, effect = {
        onDispose {
            mediaPlayer.stop()
            isPlaying.value = false
        }
    })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = song.name,
            style = MaterialTheme.typography.h5
        )

        Spacer(modifier = Modifier.height(32.dp))

        Slider(
            value = progress,
            onValueChange = { newProgress ->
                progress = newProgress
                mediaPlayer.seekTo((newProgress * song.duration).toInt())
            },
            valueRange = 0f..song.duration.toFloat(),
            steps = 0
        )

        Spacer(modifier = Modifier.height(32.dp))

        IconButton(onClick = {
            if (isPlaying.value) {
                mediaPlayer.pause()
            } else {
                mediaPlayer.start()
            }
            isPlaying.value = !isPlaying.value
        }) {
            Icon(
                imageVector = if (isPlaying.value) Icons.Filled.Face else Icons.Filled.PlayArrow,
                contentDescription = if (isPlaying.value) "Pause" else "Play"
            )
        }

        LaunchedEffect(key1 = isPlaying.value, block = {
            while (isPlaying.value && mediaPlayer.isPlaying) {
                progress = mediaPlayer.currentPosition.toFloat() / song.duration
                delay(1000)
            }
        })
    }
}