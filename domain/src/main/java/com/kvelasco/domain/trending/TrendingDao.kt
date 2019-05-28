package com.kvelasco.domain.trending

import com.kvelasco.domain.trending.movies.TrendingMovieEntity
import com.kvelasco.domain.trending.shows.TrendingShowEntity
import io.reactivex.Flowable

interface TrendingDao {

    fun saveTrendingMovies(vararg list: TrendingMovieEntity)

    fun getTrendingMovies(): Flowable<List<TrendingMovieEntity>>

    fun getTrendingShows(): Flowable<List<TrendingShowEntity>>

    fun saveTrendingShows(vararg list: TrendingShowEntity)
}