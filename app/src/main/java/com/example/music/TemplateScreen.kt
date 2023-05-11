package com.example.music

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun TemplateScreen(title: String, content: @Composable () -> Unit,contentIsEmpty: Boolean){
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        Text(text = title)
                    }
                },
                backgroundColor = Color.DarkGray,
                contentColor = MaterialTheme.colors.onPrimary,
            )
        },
        content = { padding ->
            if(!contentIsEmpty)
                Box(modifier = Modifier.padding(padding)) {
                    content()
                }
            else{
                Box(modifier = Modifier.fillMaxSize()
                    .padding(padding),
                    contentAlignment = Alignment.Center) {
                    Text(text = "Something went wrong while fetching content!")
                }
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

