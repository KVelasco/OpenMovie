package com.kvelasco.openmovie.di

import com.kvelasco.core.ratelimiters.TimeRateLimiter
import com.kvelasco.domain.trending.TrendingInteractor
import com.kvelasco.domain.trending.TrendingRepository
import com.kvelasco.domain.trending.TrendingUseCases
import dagger.Module
import dagger.Provides
import java.util.concurrent.TimeUnit

@Module
object UseCasesModule {

    @Provides
    @JvmStatic
    fun providesTrendingUseCases(repository: TrendingRepository): TrendingUseCases {
        return TrendingInteractor(repository, TimeRateLimiter(1, TimeUnit.MINUTES))
    }
}