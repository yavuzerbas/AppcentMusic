package com.example.music.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.music.screens.album_screen.ui.AlbumDetailsScreen
import com.example.music.screens.aritsts_screen.ui.ArtistsScreen
import com.example.music.screens.artist_detail_screen.ui.ArtistDetailScreen
import com.example.music.screens.category_screen.ui.CategoryScreen
import com.example.music.screens.logo_screen.view.LogoScreen

@Composable
fun MyAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "logo_screen"
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable("logo_screen") {
            // Navigate to CategoryScreen with myGenres as an argument
            LogoScreen(navController = navController)
        }
        composable("category_screen/{args}") { backStackEntry ->
            val myGenres = backStackEntry.arguments?.getString("args")
            if (myGenres != null) {
                CategoryScreen(navController,myGenres)
            }
        }
        composable("artists_screen/{args}") { backStackEntry ->
            val genreId = backStackEntry.arguments?.getString("args")
            if (genreId != null) {
                ArtistsScreen(navController,genreId)
            }
        }
        composable("artist_detail_screen/{args}") { backStackEntry ->
            val artist = backStackEntry.arguments?.getString("args")
            if (artist != null) {
                ArtistDetailScreen(navController,artist)
            }
        }
        composable("album_details_screen/{args}") { backStackEntry ->
            val album = backStackEntry.arguments?.getString("args")
            if (album != null) {
                AlbumDetailsScreen(navController,album)
            }
        }
    }
}