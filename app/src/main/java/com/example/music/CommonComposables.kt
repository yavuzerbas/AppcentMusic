package com.example.music

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.AsyncImage
import com.example.music.navigation.Screen
import com.example.music.ui.theme.YTMusic
import com.example.music.ui.theme.YTMusicPurple
import com.example.music.ui.theme.colorIndex
import com.example.music.ui.theme.colorList

@Composable
fun TemplateScreen(navController:NavController, title: String, content: @Composable () -> Unit,contentIsEmpty: Boolean){

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        Text(text = title)
                    }
                },
                backgroundColor = YTMusic,
                contentColor = MaterialTheme.colors.onPrimary,
            )
        },
        content = { padding ->
            if(!contentIsEmpty)
                Box(modifier = Modifier.padding(padding).background(YTMusicPurple)) {
                    content()
                }
            else{
                Box(modifier = Modifier.fillMaxSize()
                    .background(YTMusicPurple)
                    .padding(padding),
                    contentAlignment = Alignment.Center) {
                    Text(text = "Something went wrong while fetching content!", color = Color.White)
                }
            }
        },
        bottomBar = {
            BottomNavigation(
                backgroundColor = YTMusic,
                contentColor = MaterialTheme.colors.onPrimary
            ) {
                BottomNavigationItem(
                    icon = {
                        Icon(
                            painterResource(id = R.drawable.musical_note),
                            contentDescription = null,
                            modifier = Modifier.scale(0.4f)
                        )
                    },
                    selected = false,
                    onClick = {}
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.Favorite, contentDescription = null) },
                    selected = false,
                    onClick = {navController.navigate("favorites_screen")}
                )
            }
        }
    )
}
@Composable
fun ContentBox(pictureText: String, pictureUrl : String, onclick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .clickable { onclick() }
    ) {
        AsyncImage(
            model = pictureUrl,
            contentDescription = "Artist Image",
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = pictureText,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .background(Color.Black.copy(alpha = 0.7f))
                .padding(8.dp)
                .fillMaxWidth(),
            color = Color.White,
            style = MaterialTheme.typography.h6
        )
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
                    /*navController.navigate(screen.route) {
                        popUpTo = navController.graph.startDestinationId
                        launchSingleTop = true
                    }*/
                }
            )
        }
    }
}
@Composable
fun LoadingScreen(navController: NavController,title: String){
    TemplateScreen(title = title, content = {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }, contentIsEmpty = false,
     navController = navController)
}
@Composable
fun MockImage(image: String,text: String) {
    // Get the current color from the list
    val currentColor = colorList[colorIndex]

    // Increment the index for the next call
    colorIndex = (colorIndex + 1) % colorList.size
    Box(
        modifier = Modifier
            .size(60.dp)
            .fillMaxHeight()
            .background(
                color = currentColor
            ) // range 0.0 to 0.5)
        //elevation = 8.dp
    ) {
        AsyncImage(
            model = image,
            contentDescription = "Artist Image",
            contentScale = androidx.compose.ui.layout.ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize()
                //.padding(end = 8.dp)
                .align(Alignment.Center),

            alpha = 0.5f
        )
        Text(text = text,
            modifier = Modifier.padding(1.dp).align(Alignment.Center),
            color = Color.Black,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Bold,
            fontSize = 10.sp)
    }
}