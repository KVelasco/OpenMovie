package com.kvelasco.retrofit.trending

import com.kvelasco.core.api.ApiResponse
import com.kvelasco.domain.trending.TrendingResults
import com.kvelasco.domain.trending.TrendingService
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path

class TrendingApiService(
    retrofit: Retrofit
) : TrendingService {

    private val original = retrofit.create(Original::class.java)

    override fun getTrendingMovies(): Single<ApiResponse<TrendingResults>> {
        return original.getTrendingMovies("movie", "day")
    }

    private interface Original {
        @GET("trending/{media_type}/{time_window}")
        fun getTrendingMovies(@Path("media_type") type: String,
                              @Path("time_window") timeWindow: String): Single<ApiResponse<TrendingResults>>
    }
}