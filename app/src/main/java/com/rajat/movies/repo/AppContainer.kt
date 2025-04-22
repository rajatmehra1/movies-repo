package com.rajat.movies.repo

import android.content.Context
import com.rajat.movies.repo.db.MovieDatabase
import com.rajat.movies.repo.domain.GetMovieUseCase
import com.rajat.movies.repo.data.MovieRepositoryImpl
import com.rajat.movies.repo.data.NetworkDataSourceImpl
import com.rajat.movies.repo.network.NetworkModule
import com.rajat.movies.repo.data.OfflineDataSourceImpl

class AppContainer(context: Context){

    private val movieDao = MovieDatabase.getInstance(context).movieDao()

    private val offlineDataSource = OfflineDataSourceImpl(movieDao)
    private val networkDataSource = NetworkDataSourceImpl(NetworkModule.apiService)

    private val repository = MovieRepositoryImpl(
        networkDataSource = networkDataSource,
        offlineDataSource = offlineDataSource
    )

    fun getMoviesUseCase() = GetMovieUseCase(repository)
}