package com.example.moviedb.network

import com.example.moviedb.model.MovieResponse
import retrofit2.http.GET

interface MovieApiInterface {
    @GET("trending/movie/day")
    suspend fun getTrendingMovies(): MovieResponse
}