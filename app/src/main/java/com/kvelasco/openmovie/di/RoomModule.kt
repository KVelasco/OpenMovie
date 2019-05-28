package com.kvelasco.openmovie.di

import android.content.Context
import androidx.room.Room
import com.kvelasco.domain.trending.TrendingDao
import com.kvelasco.room.OpenMovieDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object RoomModule {

    @Provides
    @Singleton
    @JvmStatic
    fun provideDatabase(context: Context): OpenMovieDatabase {
        return Room.databaseBuilder(context, OpenMovieDatabase::class.java, "open_movie.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    @JvmStatic
    fun provideTrendingDao(db: OpenMovieDatabase): TrendingDao {
        return db.trendingMoviesDao()
    }
}