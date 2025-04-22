package com.rajat.movies.repo.data

fun MovieItem.toEntity() = MovieEntity(
    id = id ?: 0,
    title = title,
    overview = overview,
    posterPath = posterPath
)

fun MovieEntity.toMovieItem() = MovieItem(
    id = id,
    title = title,
    overview = overview,
    posterPath = posterPath
)