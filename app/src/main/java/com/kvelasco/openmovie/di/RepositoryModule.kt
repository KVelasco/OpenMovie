package com.kvelasco.openmovie.di

import com.kvelasco.domain.trending.TrendingDao
import com.kvelasco.domain.trending.TrendingRepository
import com.kvelasco.domain.trending.TrendingRepositoryImpl
import com.kvelasco.domain.trending.TrendingService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object RepositoryModule {

    @Provides
    @Singleton
    @JvmStatic
    fun providesTrendingRepository(dao: TrendingDao, service: TrendingService): TrendingRepository {
        return TrendingRepositoryImpl(dao, service)
    }
}