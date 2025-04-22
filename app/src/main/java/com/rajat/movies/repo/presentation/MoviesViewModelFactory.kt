package com.rajat.movies.repo.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rajat.movies.repo.AppContainer

class MoviesViewModelFactory(private val appContainer: AppContainer) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MoviesViewModel(appContainer.getMoviesUseCase()) as T
    }
}