package com.kvelasco.core.rxjava2

import com.kvelasco.core.api.Resource
import com.kvelasco.core.executors.DragonExecutors
import io.reactivex.Flowable

abstract class AbstractQueryResource<ResultType, RequestType> : AbstractNetworkBoundResource<ResultType, RequestType>() {

    override fun execute(): Flowable<Resource<ResultType>> {
        return loadFromLocal()
            .subscribeOn(DragonExecutors.diskIO().toScheduler())
            .flatMap { data ->
                if (shouldFetch(data)) {
                    fetchFromNetwork(data)
                } else {
                    Flowable.just(Resource.success(data))
                }
            }
            .observeOn(DragonExecutors.mainThread().toScheduler())
            .startWith(Resource.loading())
    }
}