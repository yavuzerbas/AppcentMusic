package com.example.music


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.example.music.screens.category_screen.controller.DeezerApiHelper
import com.example.music.screens.logo_screen.view.LogoScreen
import com.example.music.ui.theme.MusicTheme


/*class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MusicTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    //Greeting("Android")
                    LogoScreen { genres ->
                        // Print the fetched genres in the console
                        println("Fetched genres: $genres")
                    }
                    LaunchedEffect(Unit) {
                        val fetchedGenres = DeezerApiHelper.fetchGenres()
                        println("Fetched genres: ${fetchedGenres.map { it.name }}")
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}
@Composable
fun GenreList(genres: List<String>) {
    LazyColumn {
        items(genres) { genre ->
            Text(text = genre)
        }
    }
}*/
/*
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MusicTheme {
        Greeting("Android")
    }
}*/
