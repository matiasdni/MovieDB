package com.example.moviedb.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedb.model.Movie
import com.example.moviedb.network.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {
    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies: StateFlow<List<Movie>> get() = _movies

    private val _loading = MutableStateFlow(true)
    val loading: StateFlow<Boolean> get() = _loading

    init {
        fetchTrendingMovies()
    }

    private fun fetchTrendingMovies() {
        viewModelScope.launch {
            try {
                _loading.value = true
                val movieResponse = ApiClient.api.getTrendingMovies()
                _movies.value = movieResponse.movies
            } catch (e: Exception) {
                // Handle the error, possibly with a State for showing error messages
                _movies.value = emptyList()  // Empty list on error
            } finally {
                _loading.value = false
            }
        }
    }
}