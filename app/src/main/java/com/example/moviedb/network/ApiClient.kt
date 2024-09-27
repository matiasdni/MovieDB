package com.example.moviedb.network

import com.example.moviedb.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = BuildConfig.BASE_URL
    private const val API_KEY = BuildConfig.API_KEY

    private val client by lazy {
        OkHttpClient.Builder().addInterceptor(AuthInterceptor(API_KEY)).build()
    }

    val api: MovieApiInterface by lazy {
        Retrofit.Builder().baseUrl(BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(MovieApiInterface::class.java)
    }
}