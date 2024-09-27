package com.example.moviedb.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.moviedb.model.Movie
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

    if (loading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        selectedMovie?.let { movie ->
            MovieDetails(movie = movie)
        } ?: run {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Failed to load movie details",
                    style = typography.bodyMedium,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun MovieDetails(movie: Movie) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
            contentDescription = movie.title,
            modifier = Modifier.size(500.dp),
            contentScale = ContentScale.Crop
        )

        Text(
            text = movie.title,
            modifier = Modifier.fillMaxWidth(),
            style = typography.headlineLarge
        )

        Text(text = movie.overview, style = typography.bodyLarge)
        Text(
            text = "Release Date: ${movie.releaseDate}",
            style = typography.bodyMedium,
            modifier = Modifier.fillMaxWidth()
        )
    }
}