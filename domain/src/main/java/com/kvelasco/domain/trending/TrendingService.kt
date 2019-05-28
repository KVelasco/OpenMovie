package com.kvelasco.domain.trending

import com.kvelasco.core.api.ApiResponse
import com.kvelasco.domain.trending.movies.TrendingMovie
import com.kvelasco.domain.trending.shows.TrendingShow
import io.reactivex.Single

interface TrendingService {
    fun getTrendingMovies(): Single<ApiResponse<TrendingResults<TrendingMovie>>>
    fun getTrendingShows(): Single<ApiResponse<TrendingResults<TrendingShow>>>
}