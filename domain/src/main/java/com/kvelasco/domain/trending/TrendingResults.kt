package com.kvelasco.domain.trending

import com.google.gson.annotations.SerializedName

data class TrendingResults(
    val page: Int,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int,
    val results: List<TrendingMovie>
)