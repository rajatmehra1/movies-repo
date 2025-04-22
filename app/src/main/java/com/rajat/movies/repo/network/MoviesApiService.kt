package com.rajat.movies.repo.network

import com.rajat.movies.repo.data.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface MoviesApiService {

    @GET("/3/trending/movie/{time_window}?language=en-US")
    suspend fun getTrendingMoviesList(@Path("time_window") timeWindow: String): MoviesResponse
}