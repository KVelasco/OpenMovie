package com.kvelasco.domain.trending.shows

data class TrendingShowDomain(
    val id: Int,
    val backdropPath: String?,
    val firstAirDate: String,
    val genreIds: List<Int>,
    val name: String,
    val originCountry: List<String>,
    val originalLanguage: String,
    val originalName: String,
    val overview: String,
    val posterPath: String?,
    val voteAverage: Float,
    val voteCount: Int,
    val popularity: Float
)