package com.kvelasco.domain.trending.movies

data class TrendingMovieDomain(
    val id: Int,
    val adult: Boolean,
    val backdropPath: String?,
    val genreIds: List<Int>,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val posterPath: String?,
    val releaseDate: String?,
    val title: String,
    val video: Boolean,
    val voteAverage: Float,
    val voteCount: Int,
    val popularity: Float
)