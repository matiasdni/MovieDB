package com.example.moviedb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.moviedb.ui.MovieListScreen
import com.example.moviedb.ui.theme.MovieDBTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieDBTheme {
                MovieListScreen()
            }
        }
    }
}