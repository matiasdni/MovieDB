package com.example.moviedb.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.moviedb.model.Movie
import com.example.moviedb.ui.components.MovieList
import com.example.moviedb.ui.theme.AppTypography
import com.example.moviedb.viewmodel.MovieViewModel

@Composable
fun MovieListScreen(
    movieViewModel: MovieViewModel = viewModel(),
    navController: NavController,
) {
    val popularMovies: List<Movie> by movieViewModel.popularMovies.collectAsState()
    val loadingMovies: Boolean by movieViewModel.loadingMovies.collectAsState()
    val errorMessage by movieViewModel.errorMessage.collectAsState()

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
                    textAlign = TextAlign.Center,
                )
            }
        )
    } else {
        MovieList(
            movies = popularMovies,
            navController = navController,
            onEndReached = { movieViewModel.fetchPopularMovies() }
        )

        if (loadingMovies) {
            return Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorScheme.background),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator(
                    color = colorScheme.primary,
                )
            }
        }
    }
}

