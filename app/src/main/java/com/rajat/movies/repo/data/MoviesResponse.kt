package com.rajat.movies.repo.data

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class MoviesResponse(
    val page: Int? = null,
    val results : List<MovieItem>,
    @SerializedName("total_pages") val totalPages : Int? = null,
    @SerializedName("total_results") val totalResults: Int? = null
)
