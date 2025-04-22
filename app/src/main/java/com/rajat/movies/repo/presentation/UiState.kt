package com.rajat.movies.repo.presentation

import com.rajat.movies.repo.data.MovieItem

sealed class UiState {

    data object Loading : UiState()

    data class Error(val throwable: Throwable) : UiState()

    data class RenderMovieList(val movieList: List<MovieItem>) : UiState()

    data object EmptyList : UiState()
}