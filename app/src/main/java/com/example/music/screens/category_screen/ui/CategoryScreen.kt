package com.example.music.screens.category_screen.ui

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.music.screens.category_screen.model.Genre
import com.example.music.screens.category_screen.model.GenresResponse
import com.google.gson.Gson
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.music.ContentBox
import com.example.music.TemplateScreen

@Composable
fun CategoryScreen(navController: NavController, encodedJsonMyGenres: String) {
    val viewModel: CategoryScreenViewModel = viewModel()
    viewModel.decodeAndDeserializeJson(encodedJsonMyGenres)

    val myGenres = viewModel.myGenres.value

    TemplateScreen(title = "Categories", content = {
        myGenres?.let {
            GenresList(navController = navController, genresResponse = it)
        }
    }, contentIsEmpty = myGenres == null, navController = navController)
}

@Composable
fun GenresList( navController: NavController,genresResponse: GenresResponse, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.padding(4.dp),
        contentPadding = PaddingValues(4.dp)
    ) {
        items(genresResponse.data) { genre ->
            GenreBox(genre,onclick = {
                val parsedGenreName = genre.name.replace(" ","_").replace("/",",")
                navController.navigate("artists_screen/${genre.id}/${parsedGenreName}")
            })
        }
    }
}
@Composable
fun GenreBox(genre: Genre, onclick: () -> Unit){
    ContentBox(pictureText = genre.name, pictureUrl = genre.pictureBig,onclick = onclick)
}