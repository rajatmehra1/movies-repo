package com.rajat.movies.repo.data

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class MovieItem(
    val title: String? = null,
    val id: Int? = null,
    val overview: String? = null,
    @SerializedName("backdrop_path") val backdropPath: String? = null,
    @SerializedName("poster_path") val posterPath: String? = null
) {

    fun getImageUrl() = "https://image.tmdb.org/t/p/w500/$posterPath"
}
