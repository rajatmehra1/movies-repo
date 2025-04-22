package com.rajat.movies.repo.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rajat.movies.repo.data.MovieItem
import com.rajat.movies.repo.domain.GetMovieUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MoviesViewModel(val useCase: GetMovieUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    private var _searchJob: Job? = null
    private val _allMovies = mutableStateListOf<MovieItem>()


    init {
        fetchMovieData()
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        _searchJob?.cancel()
        _searchJob = viewModelScope.launch {
            delay(300)
            val filtered = if (query.isBlank()) _allMovies
            else _allMovies.filter {
                it.title?.contains(query, ignoreCase = true) == true
            }
            _uiState.value = UiState.RenderMovieList(filtered)
        }
    }

    private fun fetchMovieData() {
        viewModelScope.launch {
            useCase()
                .onStart { _uiState.value = UiState.Loading }
                .catch {
                    _uiState.value = UiState.Error(it)
                }.collect {
                    _uiState.value = if (it.isEmpty()) UiState.EmptyList else UiState.RenderMovieList(it)
                    _allMovies.clear()
                    _allMovies.addAll(it)
                }
        }
    }

    fun retry() {
        fetchMovieData()
    }
}