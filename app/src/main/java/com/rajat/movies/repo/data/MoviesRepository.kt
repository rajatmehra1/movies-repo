package com.rajat.movies.repo.data

import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun getMovies(): Flow<List<MovieItem>>
}