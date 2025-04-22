package com.rajat.movies.repo.data

interface NetworkDataSource {

    suspend fun getMoviesList(): MoviesResponse
}