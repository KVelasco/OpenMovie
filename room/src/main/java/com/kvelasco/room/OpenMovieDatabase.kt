package com.kvelasco.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kvelasco.domain.trending.movies.TrendingMovieEntity
import com.kvelasco.domain.trending.shows.TrendingShowEntity
import com.kvelasco.room.trending.TrendingRoomDao

@Database(
    entities = [
        TrendingMovieEntity::class,
        TrendingShowEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(RoomConverters::class)
abstract class OpenMovieDatabase : RoomDatabase() {
    abstract fun trendingMoviesDao(): TrendingRoomDao
}