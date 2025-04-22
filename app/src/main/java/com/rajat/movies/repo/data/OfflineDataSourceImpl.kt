package com.rajat.movies.repo.data

import com.rajat.movies.repo.db.MovieDao
import kotlinx.coroutines.flow.Flow

class OfflineDataSourceImpl(private val movieDao: MovieDao) : OfflineDataSource {

    override fun getMovies(): Flow<List<MovieEntity>> {
        return movieDao.getAllMovies()
    }

    override suspend fun saveMovies(movies: MoviesResponse) {
        movieDao.run {
            clearAll()
            insertMovies(movies.results.map { it.toEntity() })
        }
    }
}