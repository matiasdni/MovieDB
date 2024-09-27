package com.example.moviedb.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.moviedb.R
import com.example.moviedb.model.Movie
import com.example.moviedb.ui.theme.AppTypography
import java.util.Locale.getDefault

@Composable
fun MovieDetails(movie: Movie) {
    val scrollState = rememberScrollState()
    val ratingStringResource = stringResource(R.string.rating)
    val locale = getDefault()

    val rating = String.format(
        locale, stringResource(R.string.rating_format), ratingStringResource, movie.vote_average
    )

    Column(
        modifier = Modifier
            .background(colorScheme.background)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        AsyncImage(
            model = stringResource(R.string.poster_baseurl) + movie.poster_path,
            contentDescription = movie.title,
            modifier = Modifier.size(500.dp),
            contentScale = ContentScale.Crop
        )

        Text(
            text = movie.title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .padding(top = 8.dp),
            style = AppTypography.headlineLarge
        )

        StarRating(voteAverage = movie.vote_average)
        Row {
            Text(
                text = rating,
                style = AppTypography.titleLarge,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }

        Text(
            text = movie.overview,
            style = AppTypography.bodyLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        )

        Text(
            text = movie.release_date,
            textAlign = TextAlign.Center,
            style = AppTypography.bodyMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    }
}