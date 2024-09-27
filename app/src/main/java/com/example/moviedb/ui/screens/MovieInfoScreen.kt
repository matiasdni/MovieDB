package com.example.moviedb.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.moviedb.ui.components.MovieDetails
import com.example.moviedb.ui.theme.AppTypography
import com.example.moviedb.viewmodel.MovieViewModel

@Composable
fun MovieInfoScreen(
    navController: NavController,
    movieId: String?,
    movieViewModel: MovieViewModel = viewModel()
) {
    LaunchedEffect(movieId) {
        movieViewModel.fetchMovieDetails(movieId!!)
    }

    val selectedMovie by movieViewModel.selectedMovie.collectAsState()
    val loading by movieViewModel.loadingMovieDetails.collectAsState()
    val errorMessage by movieViewModel.errorMessage.collectAsState()

    if (loading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colorScheme.background),
            contentAlignment = Alignment.Center,
            content = {
                CircularProgressIndicator(
                    modifier = Modifier.size(60.dp),
                    color = colorScheme.primaryContainer
                )
            }
        )
    }

    if (errorMessage.asString().isNotEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colorScheme.errorContainer),
            contentAlignment = Alignment.Center,
            content = {
                Text(
                    text = errorMessage.asString(),
                    style = AppTypography.bodyMedium,
                    color = colorScheme.onErrorContainer,
                    textAlign = TextAlign.Center
                )
            }
        )
    } else {
        selectedMovie?.let { movie ->
            MovieDetails(movie = movie)
        }
    }
}

