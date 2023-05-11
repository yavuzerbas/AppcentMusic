package com.example.music.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.music.screens.aritst_screen.view.ArtistsScreen
import com.example.music.screens.category_screen.view.CategoryScreen
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
            val myArtists = backStackEntry.arguments?.getString("args")
            if (myArtists != null) {
                ArtistsScreen(navController,myArtists)
            }
        }

    }
}