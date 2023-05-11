package com.example.music

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.*
import com.example.music.screens.category_screen.model.GenresResponse
import com.example.music.screens.category_screen.view.CategoryScreen
import com.example.music.screens.logo_screen.view.LogoScreen
import com.example.music.ui.theme.MusicTheme
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.music.navigation.Screen


class MainActivity : ComponentActivity() {
    private val handler = Handler(Looper.getMainLooper())

    private val hideSystemBarsRunnable = Runnable {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.let {
                it.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
            }
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_IMMERSIVE
                            or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_FULLSCREEN
                    )
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideSystemBars()

        window.decorView.setOnSystemUiVisibilityChangeListener { visibility ->
            if (visibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0) {
                handler.removeCallbacks(hideSystemBarsRunnable)
                handler.postDelayed(hideSystemBarsRunnable, 3000)
            }
        }

        setContent {
            // your compose code here
        }
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
    private fun hideSystemBars() {
        handler.post(hideSystemBarsRunnable)
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
@Composable
fun CustomNavigationBar(navController: NavController, items: List<Screen>) {
    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.arguments?.getString("route")

        items.forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                label = { Text(screen.route) },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo = navController.graph.startDestinationId
                        launchSingleTop = true
                    }
                }
            )
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
