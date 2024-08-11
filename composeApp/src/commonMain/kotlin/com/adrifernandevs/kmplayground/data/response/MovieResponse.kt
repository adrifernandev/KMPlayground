package com.adrifernandevs.kmplayground.data.response

import com.adrifernandevs.kmplayground.domain.model.Movie
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieResponseResult(
    val page: Int,
    val results: List<MovieResponse>,
    @SerialName("total_pages") val totalPages: Int,
    @SerialName("total_results") val totalResults: Int
)

@Serializable
data class MovieResponse(
    val id: Int,
    val title: String,
    val overview: String,
    @SerialName("release_date") val releaseDate: String,
    @SerialName("poster_path") val posterPath: String,
    @SerialName("backdrop_path") val backdropPath: String?,
    @SerialName("original_title") val originalTitle: String,
    @SerialName("original_language") val originalLanguage: String,
    val popularity: Double,
    @SerialName("vote_average") val voteAverage: Double,
)

fun MovieResponse.toMovie() = Movie(
    id = id,
    title = title,
    poster = "https://image.tmdb.org/t/p/w500$posterPath",
    overview = overview,
    releaseDate = releaseDate,
    backdrop = backdropPath?.let { "https://image.tmdb.org/t/p/w780$it" },
    originalTitle = originalTitle,
    originalLanguage = originalLanguage,
    popularity = popularity,
    voteAverage = voteAverage
)

fun List<MovieResponse>.toMovieList() = map { it.toMovie() }