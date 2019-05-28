package com.kvelasco.openmovie.trending

import com.kvelasco.domain.trending.movies.TrendingMovieDomain
import com.kvelasco.domain.trending.shows.TrendingShowDomain
import com.kvelasco.openmovie.common.ImageSizes

data class TrendingUiModel(
    val id: Int,
    val posterPath: String?
)

fun TrendingMovieDomain.toUiModel(): TrendingUiModel {
    return TrendingUiModel(
        id = id,
        posterPath = "${ImageSizes.baseImageUrl}/w342/$posterPath"
    )
}

fun TrendingShowDomain.toUiModel(): TrendingUiModel {
    return TrendingUiModel(
        id = id,
        posterPath = "${ImageSizes.baseImageUrl}/w780/$backdropPath"
    )
}