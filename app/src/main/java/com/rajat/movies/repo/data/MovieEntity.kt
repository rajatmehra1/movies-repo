package com.rajat.movies.repo.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(val title: String? = null,
                       @PrimaryKey val id: Int,
                       val overview: String? = null,
                       val posterPath: String? = null)