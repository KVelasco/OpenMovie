package com.kvelasco.domain.trending

import io.reactivex.Flowable

interface TrendingDao {

    fun saveTrendingMovies(vararg list: TrendingMovieEntity)

    fun getTrendingMovies(): Flowable<List<TrendingMovieEntity>>
}