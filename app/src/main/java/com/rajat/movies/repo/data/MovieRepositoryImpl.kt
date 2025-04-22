package com.rajat.movies.repo.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class MovieRepositoryImpl(
    private val networkDataSource: NetworkDataSource,
    private val offlineDataSource: OfflineDataSource
) : MoviesRepository {

    override fun getMovies(): Flow<List<MovieItem>> = flow {
        try {
            val remoteMovies = networkDataSource.getMoviesList()
            offlineDataSource.saveMovies(remoteMovies)
        } catch (e: Exception) {
            //continue and loook for offline data
        }

        emitAll(
            offlineDataSource.getMovies().map { it.map { movieEntity -> movieEntity.toMovieItem() } }
        )
    }.flowOn(Dispatchers.IO)
}