package com.kvelasco.domain.trending

import com.google.gson.annotations.SerializedName
import com.kvelasco.domain.trending.movies.TrendingMovie

data class TrendingResults<T>(
    val page: Int,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int,
    val results: List<T>
)