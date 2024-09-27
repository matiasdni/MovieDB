package com.example.moviedb.network

import com.example.moviedb.model.Movie
import com.example.moviedb.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApiInterface {
    @GET("trending/movie/day")
    suspend fun getTrendingMovies(): MovieResponse

    @GET("movie/{movieId}")
    suspend fun getMovieDetails(@Path("movieId") movieId: String): Movie
}