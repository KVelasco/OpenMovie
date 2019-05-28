package com.kvelasco.domain.trending

import com.kvelasco.core.api.Resource
import com.kvelasco.core.ratelimiters.RateLimiter
import com.kvelasco.domain.trending.movies.TrendingMovie
import com.kvelasco.domain.trending.shows.TrendingShow
import io.reactivex.Flowable

interface TrendingRepository {
    fun getTrendingMovies(rateLimiter: RateLimiter<Any>): Flowable<Resource<List<TrendingMovie>>>
    fun getTrendingShows(rateLimiter: RateLimiter<Any>): Flowable<Resource<List<TrendingShow>>>

}