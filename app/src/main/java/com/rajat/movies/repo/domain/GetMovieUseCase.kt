package com.rajat.movies.repo.domain

import com.rajat.movies.repo.data.MoviesRepository

class GetMovieUseCase(private val moviesRepository: MoviesRepository) {

    operator fun invoke() = moviesRepository.getMovies()
}