package com.kvelasco.domain.trending

import com.kvelasco.core.api.Resource
import com.kvelasco.core.ratelimiters.RateLimiter
import io.reactivex.Flowable

class TrendingInteractor(
    private val repository: TrendingRepository,
    private val rateLimiter: RateLimiter<Any>
) : TrendingUseCases {

    override fun getTrendingMovies(): Flowable<Resource<List<TrendingMovieDomain>>> {
        return repository.getTrendingMovies(rateLimiter)
            .map { resource ->
                if (resource.data == null) {
                    resource.clone()
                } else {
                    resource.clone(resource.data!!.map { it.toTrendingDomain() })
                }
            }.onErrorReturn {
                Resource.error(it, null)
            }
    }

}