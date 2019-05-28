package com.kvelasco.domain.trending

import com.kvelasco.core.api.Resource
import com.kvelasco.core.ratelimiters.RateLimiter
import io.reactivex.Flowable

interface TrendingRepository {
    fun getTrendingMovies(rateLimiter: RateLimiter<Any>): Flowable<Resource<List<TrendingMovie>>>
}