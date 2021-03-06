package com.kvelasco.room.trending

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kvelasco.domain.trending.TrendingDao
import com.kvelasco.domain.trending.movies.TrendingMovieEntity
import com.kvelasco.domain.trending.shows.TrendingShowEntity
import io.reactivex.Flowable

@Dao
abstract class TrendingRoomDao : TrendingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override fun saveTrendingMovies(vararg list: TrendingMovieEntity)

    @Query("SELECT * FROM TRENDING_MOVIES")
    abstract override fun getTrendingMovies(): Flowable<List<TrendingMovieEntity>>

    @Query("SELECT * FROM TRENDING_SHOWS")
    abstract override fun getTrendingShows(): Flowable<List<TrendingShowEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override fun saveTrendingShows(vararg list: TrendingShowEntity)
}