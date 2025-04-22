package com.rajat.movies.repo.data

import com.rajat.movies.repo.network.MoviesApiService

class NetworkDataSourceImpl(private val moviesApiService: MoviesApiService) : NetworkDataSource {

    override suspend fun getMoviesList(): MoviesResponse =
        moviesApiService.getTrendingMoviesList("day")
}