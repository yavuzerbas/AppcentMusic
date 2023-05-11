package com.example.music

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.music.screens.category_screen.model.GenresResponse
import com.example.music.screens.category_screen.view.CategoryScreen
import com.example.music.screens.logo_screen.view.LogoScreen
import com.example.music.ui.theme.MusicTheme
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.music.navigation.Screen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MusicTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MyAppNavHost()
                }
            }
        }
    }
}
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

    }
}
/*
LogoScreen(
    onNavigateToGenres = { navController.navigate("category_screen") },
    /*...*/
)
 */
/*        composable("profile") {
            ProfileScreen(
                onNavigateToFriends = { navController.navigate("friendsList") },
                *//*...*//*
            )
        }*/
//composable("friendslist") { FriendsListScreen(/*...*/) }
/*@Composable
fun ProfileScreen(
    onNavigateToFriends: () -> Unit,
    *//*...*//*
) {
    *//*...*//*
    Button(onClick = onNavigateToFriends) {
        Text(text = "See friends list")
    }
}*/
