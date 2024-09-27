package com.example.moviedb.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedb.R
import com.example.moviedb.model.Movie
import com.example.moviedb.network.ApiClient
import com.example.moviedb.util.UiText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

fun formatReleaseDate(releaseDate: String): String {
    return releaseDate.split("-").reversed().joinToString("/")
}

class MovieViewModel : ViewModel() {
    private val _popularMovies = MutableStateFlow<List<Movie>>(emptyList())
    val popularMovies: StateFlow<List<Movie>> get() = _popularMovies

    private val _loadingMovies = MutableStateFlow(false)
    val loadingMovies: StateFlow<Boolean> get() = _loadingMovies

    private val _selectedMovie = MutableStateFlow<Movie?>(null)
    val selectedMovie: StateFlow<Movie?> get() = _selectedMovie

    private val _loadingMovieDetails = MutableStateFlow(false)
    val loadingMovieDetails: StateFlow<Boolean> get() = _loadingMovieDetails

    private val _errorMessage =
        MutableStateFlow<UiText>(UiText.StringResource(R.string.empty_string))
    val errorMessage: StateFlow<UiText> = _errorMessage

    private var currentPage = 1
    private var canLoadMore = true

    init {
        fetchPopularMovies()
    }

    fun fetchPopularMovies() {
        if (_loadingMovies.value || !canLoadMore) return

        viewModelScope.launch {
            try {
                _loadingMovies.value = true
                val movieResponse = ApiClient.api.getPopularMovies(page = currentPage)

                if (movieResponse.results.isEmpty()) canLoadMore = false

                val movies = movieResponse.results.map { movie: Movie ->
                    movie.copy(release_date = formatReleaseDate(movie.release_date))
                }

                _popularMovies.value += movies
                _errorMessage.value = UiText.StringResource(resId = R.string.empty_string)
                currentPage++
            } catch (e: Exception) {
                _popularMovies.value = emptyList()
                _errorMessage.value = UiText.StringResource(resId = R.string.error_message_movies)
            } finally {
                _loadingMovies.value = false
            }
        }
    }

    fun fetchMovieDetails(movieId: String) {
        viewModelScope.launch {
            try {
                _loadingMovieDetails.value = true
                val response = ApiClient.api.getMovieDetails(movieId)
                val movie = response.copy(release_date = formatReleaseDate(response.release_date))
                _selectedMovie.value = movie
                _errorMessage.value = UiText.StringResource(resId = R.string.empty_string)
            } catch (e: Exception) {
                _selectedMovie.value = null
                _errorMessage.value = UiText.StringResource(resId = R.string.error_message_details)
            } finally {
                _loadingMovieDetails.value = false
            }
        }
    }
}