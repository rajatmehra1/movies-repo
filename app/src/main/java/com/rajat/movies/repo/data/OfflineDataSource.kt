package com.rajat.movies.repo.data

import kotlinx.coroutines.flow.Flow

interface OfflineDataSource {

    fun getMovies(): Flow<List<MovieEntity>>

    suspend fun saveMovies(movies: MoviesResponse)
}