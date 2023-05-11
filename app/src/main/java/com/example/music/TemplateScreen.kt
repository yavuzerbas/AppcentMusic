package com.example.music

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

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

