package com.example.moviedb.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedb.model.Movie
import com.example.moviedb.network.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

fun formatReleaseDate(releaseDate: String): String {
    return releaseDate.split("-").reversed().joinToString("/")
}

class MovieViewModel : ViewModel() {
    private val _trendingMovies = MutableStateFlow<List<Movie>>(emptyList())
    val trendingMovies: StateFlow<List<Movie>> get() = _trendingMovies

    private val _loadingTrendingMovies = MutableStateFlow(true)
    val loadingTrendingMovies: StateFlow<Boolean> get() = _loadingTrendingMovies

    private val _selectedMovie = MutableStateFlow<Movie?>(null)
    val selectedMovie: StateFlow<Movie?> get() = _selectedMovie

    private val _loadingMovieDetails = MutableStateFlow(false)
    val loadingMovieDetails: StateFlow<Boolean> get() = _loadingMovieDetails

    init {
        fetchTrendingMovies()
    }

    private fun fetchTrendingMovies() {
        viewModelScope.launch {
            try {
                _loadingTrendingMovies.value = true
                val movieResponse = ApiClient.api.getTrendingMovies()

                val movies = movieResponse.movies.map { movie ->
                    movie.copy(releaseDate = formatReleaseDate(movie.releaseDate))
                }

                _trendingMovies.value = movies
            } catch (e: Exception) {
                _trendingMovies.value = emptyList()
            } finally {
                _loadingTrendingMovies.value = false
            }
        }
    }

    fun fetchMovieDetails(movieId: String) {
        viewModelScope.launch {
            try {
                _loadingMovieDetails.value = true
                val response = ApiClient.api.getMovieDetails(movieId)
                val movie = response.copy(releaseDate = formatReleaseDate(response.releaseDate))
                _selectedMovie.value = movie
            } catch (e: Exception) {
                _selectedMovie.value = null
            } finally {
                _loadingMovieDetails.value = false
            }
        }
    }
}