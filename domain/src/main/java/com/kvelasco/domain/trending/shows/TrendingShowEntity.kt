package com.kvelasco.domain.trending.shows

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trending_shows")
data class TrendingShowEntity(
    @PrimaryKey val id: Int,
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

fun TrendingShowEntity.toShowEntity(): TrendingShow {
    return TrendingShow(
        id,
        backdropPath,
        firstAirDate,
        genreIds,
        name,
        originCountry,
        originalLanguage,
        originalName,
        overview,
        posterPath,
        voteAverage,
        voteCount,
        popularity
    )
}