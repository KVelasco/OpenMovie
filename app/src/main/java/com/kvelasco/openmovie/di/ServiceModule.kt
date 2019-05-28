package com.kvelasco.openmovie.di

import com.kvelasco.domain.trending.TrendingService
import com.kvelasco.retrofit.trending.TrendingApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
object ServiceModule {

    @Provides
    @Singleton
    @JvmStatic
    fun providesTrendingService(retrofit: Retrofit): TrendingService {
        return TrendingApiService(retrofit)
    }
}