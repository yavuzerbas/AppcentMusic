package com.example.music.screens.logo_screen.view

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.music.screens.category_screen.data.DeezerCategoryApiHelper
import com.example.music.ui.theme.YTMusic
import com.google.gson.Gson

@Composable
fun LogoScreen(navController: NavController) {
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        val myGenres = DeezerCategoryApiHelper.fetchGenres()

        // Convert myGenres to a JSON string
        val gson = Gson()
        val jsonMyGenres = gson.toJson(myGenres)

        // Encode the JSON string
        val encodedJsonMyGenres = Uri.encode(jsonMyGenres)

        // Navigate to CategoryScreen with myGenres as an argument
        navController.navigate("category_screen/$encodedJsonMyGenres") {
            popUpTo("logo_screen") {
                inclusive = true
            }
        }
    }
    Box(
        modifier = Modifier
            .background(YTMusic)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = com.example.music.R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .fillMaxWidth(0.80f) // This makes the Image take up half of the screen width
                .aspectRatio(1f, matchHeightConstraintsFirst = true)
        )
    }
}


