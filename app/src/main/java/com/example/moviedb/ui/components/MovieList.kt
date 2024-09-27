package com.example.moviedb.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.moviedb.R
import com.example.moviedb.model.Movie

@Composable
fun MovieList(
    movies: List<Movie>,
    navController: NavController,
    onEndReached: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        itemsIndexed(movies) { index, movie ->
            if (index >= movies.size - 1) {
                onEndReached()
            }
            MovieItem(movie = movie, navController = navController)
        }
    }
}

@Composable
fun MovieItem(movie: Movie, navController: NavController) {
    val movieRoute = stringResource(R.string.movie_details_route) + movie.id

    fun handleMovieClick(): () -> Unit = {
        navController.navigate(movieRoute)
    }
    MovieCard(movie, handleMovieClick())
}