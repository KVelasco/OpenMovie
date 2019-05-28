package com.kvelasco.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kvelasco.domain.trending.TrendingMovieEntity
import com.kvelasco.room.trending.TrendingRoomDao

@Database(
    entities = [
        TrendingMovieEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(RoomConverters::class)
abstract class OpenMovieDatabase : RoomDatabase() {
    abstract fun trendingMoviesDao(): TrendingRoomDao
}