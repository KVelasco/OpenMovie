package com.kvelasco.domain.trending

import com.kvelasco.core.api.ApiResponse
import io.reactivex.Single

interface TrendingService {
    fun getTrendingMovies(): Single<ApiResponse<TrendingResults>>
}