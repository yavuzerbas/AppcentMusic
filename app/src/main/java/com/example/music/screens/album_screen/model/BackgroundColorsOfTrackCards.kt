package com.example.music.screens.album_screen.model

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.ui.graphics.Color
import com.example.music.ui.theme.LightGreen

class BackgroundColorsOfTrackCards{
    val trackBackgroundColors = mutableStateMapOf<Int, Color>()

    fun addColor(id: Int){
        trackBackgroundColors[id] = Color.DarkGray
    }
    fun toggleColor(id: Int){
        trackBackgroundColors[id] = if(trackBackgroundColors[id] == Color.DarkGray) LightGreen else Color.DarkGray
    }
    fun getColor(id: Int): Color {
        return trackBackgroundColors[id] ?: Color.DarkGray
    }
    fun makeColorDarkGray(id: Int){
        if(id != -1)
            trackBackgroundColors[id] = Color.DarkGray
    }
}