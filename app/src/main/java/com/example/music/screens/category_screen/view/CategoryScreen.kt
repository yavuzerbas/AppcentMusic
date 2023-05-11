package com.example.music.screens.category_screen.view

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import com.example.music.screens.category_screen.model.Genre
import com.example.music.screens.category_screen.model.GenresResponse
import com.google.gson.Gson
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

/*@Composable
fun CategoryScreen(navController: NavController, string: String) {
    val encodedJsonMyGenres = navController.previousBackStackEntry?.arguments?.getString("serializedGenresResponse")
    val jsonMyGenres = encodedJsonMyGenres?.let { Uri.decode(it) }
    val gson = Gson()
    val myGenres = jsonMyGenres?.let { gson.fromJson(it, GenresResponse::class.java) }


    if (myGenres != null) {
        Text(text = myGenres.data[0].name)
    }
}*/
@Composable
fun CategoryScreen(navController: NavController, encodedJsonMyGenres: String) {
    // Decode the JSON string
    val jsonMyGenres = Uri.decode(encodedJsonMyGenres)

    // Deserialize the JSON string back to GenresResponse object
    val gson = Gson()
    val myGenres = gson.fromJson(jsonMyGenres, GenresResponse::class.java)

    // Display the first genre name
    if (myGenres != null) {
        //Text(text = myGenres.data[0].name)
        GenresList(genresResponse = myGenres)
    }
}
@Composable
fun GenresList(genresResponse: GenresResponse) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.padding(4.dp),
        contentPadding = PaddingValues(4.dp)
    ) {
        items(genresResponse.data) { genre ->
            genreBox(genre)
        }
    }
}
@Composable
fun genreBox(genre: Genre){
    Box(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth().
            clip(RoundedCornerShape(10.dp))
    ) {
        AsyncImage(model = genre.pictureBig, contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
        Text(
            text = genre.name,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .background(Color.Black.copy(alpha = 0.7f))
                .padding(8.dp).
                fillMaxWidth()
            ,
            color = androidx.compose.ui.graphics.Color.White,
            style = TextStyle(shadow = Shadow(color = Color.Black, blurRadius = 10f))
        )
    }
}

