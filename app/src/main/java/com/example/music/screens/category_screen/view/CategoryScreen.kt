package com.example.music.screens.category_screen.view

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import com.example.music.screens.category_screen.model.Genre
import com.example.music.screens.category_screen.model.GenresResponse
import com.google.gson.Gson
import androidx.compose.ui.Modifier
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
    LazyColumn {
        items(genresResponse.data) { genre ->
            GenreCard(genre)
        }
    }
}

@Composable
fun GenreCard(genre: Genre) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = 8.dp
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            AsyncImage(model = genre.picture, contentDescription = null, modifier = Modifier.align(
                Alignment.CenterHorizontally)
            )
            Text(text = genre.name)
        }
    }
}

//List of boxes in a column
@Composable
fun CategoryColumn() {

}

@Composable
fun CategoryBox(){

}