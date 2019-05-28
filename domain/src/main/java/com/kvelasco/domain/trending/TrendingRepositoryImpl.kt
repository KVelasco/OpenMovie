package com.kvelasco.domain.trending

import com.kvelasco.core.api.ApiResponse
import com.kvelasco.core.api.Resource
import com.kvelasco.core.ratelimiters.RateLimiter
import com.kvelasco.core.rxjava2.AbstractQueryResource
import io.reactivex.Flowable

class TrendingRepositoryImpl(
    private val dao: TrendingDao,
    private val service: TrendingService
) : TrendingRepository {

    override fun getTrendingMovies(rateLimiter: RateLimiter<Any>): Flowable<Resource<List<TrendingMovie>>> {
        return object : AbstractQueryResource<List<TrendingMovie>, List<TrendingMovie>>() {
            override fun shouldFetch(data: List<TrendingMovie>): Boolean {
                return rateLimiter.shouldFetch("movies")
            }

            override fun saveToLocal(data: List<TrendingMovie>) {
               dao.saveTrendingMovies(*data.map { it.toTrendingEntity() }.toTypedArray())
            }

            override fun loadFromLocal(): Flowable<List<TrendingMovie>> {
                return dao.getTrendingMovies()
                    .map {
                        it.map { entity ->
                            entity.toTrendingMovie()
                        }
                    }
            }

            override fun createApiCall(): Flowable<ApiResponse<List<TrendingMovie>>> {
                return service.getTrendingMovies()
                    .toFlowable()
                    .map {
                        it.copy(body = it.body?.results)
                    }
            }
        }.toFlowable()
    }
}