package com.kvelasco.openmovie.trending

import com.kvelasco.domain.trending.TrendingMovieDomain
import com.kvelasco.openmovie.common.ImageSizes

data class TrendingUiModel(
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

fun TrendingMovieDomain.toUiModel(): TrendingUiModel {
    return TrendingUiModel(
        id = id,
        adult = adult,
        backdropPath = "${ImageSizes.baseImageUrl}/w300/$backdropPath",
        genreIds = genreIds,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        overview = overview,
        posterPath = "${ImageSizes.baseImageUrl}/w185/$posterPath",
        releaseDate = releaseDate,
        title = title,
        video = video,
        voteAverage = voteAverage,
        voteCount = voteCount,
        popularity = popularity
    )
}