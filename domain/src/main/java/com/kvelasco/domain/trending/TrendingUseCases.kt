package com.kvelasco.domain.trending

import com.kvelasco.core.api.Resource
import com.kvelasco.domain.trending.movies.TrendingMovieDomain
import com.kvelasco.domain.trending.shows.TrendingShowDomain
import io.reactivex.Flowable

interface TrendingUseCases {
    fun getTrendingMovies(): Flowable<Resource<List<TrendingMovieDomain>>>
    fun getTrendingShows(): Flowable<Resource<List<TrendingShowDomain>>>
}