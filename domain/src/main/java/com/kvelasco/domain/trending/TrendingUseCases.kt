package com.kvelasco.domain.trending

import com.kvelasco.core.api.Resource
import io.reactivex.Flowable

interface TrendingUseCases {
    fun getTrendingMovies(): Flowable<Resource<List<TrendingMovieDomain>>>
}