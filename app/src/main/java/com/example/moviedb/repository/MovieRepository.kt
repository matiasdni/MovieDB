package com.example.moviedb.repository

import com.example.moviedb.model.MovieResponse
import com.example.moviedb.network.ApiClient

class MovieRepository {
    suspend fun getTrendingMovies(): MovieResponse {
        return ApiClient.api.getTrendingMovies()
    }
}