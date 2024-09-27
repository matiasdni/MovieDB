package com.example.moviedb.network

import com.example.moviedb.BuildConfig
import com.example.moviedb.model.Movie
import com.example.moviedb.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val popularMoviesQuery = BuildConfig.POPULAR_MOVIES_QUERY
const val movieDetailsQuery = BuildConfig.MOVIE_DETAILS_QUERY

interface MovieApiInterface {
    @GET(popularMoviesQuery)
    suspend fun getPopularMovies(@Query("page") page: Int): MovieResponse

    @GET(movieDetailsQuery)
    suspend fun getMovieDetails(@Path("movieId") movieId: String): Movie
}