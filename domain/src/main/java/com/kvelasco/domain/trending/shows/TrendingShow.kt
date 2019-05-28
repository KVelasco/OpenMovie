package com.kvelasco.domain.trending.shows

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class TrendingShow(
    val id: Int,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("first_air_date") val firstAirDate: String,
    @SerializedName("genre_ids") val genreIds: List<Int>,
    val name: String,
    @SerializedName("origin_country") val originCountry: List<String>,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("original_name") val originalName: String,
    val overview: String,
    val posterPath: String?,
    @SerializedName("vote_average") val voteAverage: Float,
    @SerializedName("vote_count") val voteCount: Int,
    @SerializedName("popularity") val popularity: Float
)

fun TrendingShow.toShowEntity(): TrendingShowEntity {
    return TrendingShowEntity(
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

fun TrendingShow.toDomain(): TrendingShowDomain {
    return TrendingShowDomain(
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